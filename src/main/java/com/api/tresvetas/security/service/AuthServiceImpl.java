package com.api.tresvetas.security.service;

import com.api.tresvetas.usuarios.dto.request.LoginRequestDTO;
import com.api.tresvetas.usuarios.dto.request.LoginVerificacionRequestDTO;
import com.api.tresvetas.usuarios.dto.request.RegistroRequestDTO;
import com.api.tresvetas.usuarios.dto.response.LoginResponseDTO;
import com.api.tresvetas.usuarios.dto.response.PersonaResponseDTO;
import com.api.tresvetas.usuarios.dto.response.RolResponseDTO;
import com.api.tresvetas.usuarios.dto.response.UsuarioResponseDTO;
import com.api.tresvetas.usuarios.entity.Persona;
import com.api.tresvetas.usuarios.entity.Rol;
import com.api.tresvetas.usuarios.entity.Usuario;
import com.api.tresvetas.usuarios.repository.PersonaRepository;
import com.api.tresvetas.usuarios.repository.RolRepository;
import com.api.tresvetas.usuarios.repository.UsuarioRepository;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final SecureRandom secureRandom = new SecureRandom();

    private final ConcurrentHashMap<String, CodigoVerificacion> codigos =
            new ConcurrentHashMap<>();

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final long codeExpiration;
    private final int maxAttempts;
    private final long resendCooldown;
    private final int maxResends;

    public AuthServiceImpl(
        AuthenticationManager authenticationManager,
        UsuarioRepository usuarioRepository,
        PersonaRepository personaRepository,
        RolRepository rolRepository,
        PasswordEncoder passwordEncoder,
        MailService mailService,
        @Value("${app.security.two-factor.code-expiration}") long codeExpiration,
        @Value("${app.security.two-factor.max-attempts}") int maxAttempts,
        @Value("${app.security.two-factor.resend-cooldown}") long resendCooldown,
        @Value("${app.security.two-factor.max-resends}") int maxResends
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.codeExpiration = codeExpiration;
        this.maxAttempts = maxAttempts;
        this.resendCooldown = resendCooldown;
        this.maxResends = maxResends;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO register(RegistroRequestDTO request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un usuario registrado con ese correo electrónico."
            );
        }

        Rol rol = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "No se ha encontrado el rol USUARIO."
                ));

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);
        usuario.setActivo(true);

        usuario = usuarioRepository.save(usuario);

        Persona persona = new Persona();
        persona.setUsuario(usuario);
        persona.setNombre(request.getPersona().getNombre());
        persona.setApellidos(request.getPersona().getApellidos());
        persona.setTelefono(request.getPersona().getTelefono());
        persona.setDni(request.getPersona().getDni());

        persona = personaRepository.save(persona);

        RolResponseDTO rolResponse = new RolResponseDTO(
                rol.getId(),
                rol.getNombre()
        );

        PersonaResponseDTO personaResponse = new PersonaResponseDTO(
                persona.getId(),
                persona.getNombre(),
                persona.getApellidos(),
                persona.getTelefono(),
                persona.getDni()
        );

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                rolResponse,
                usuario.getActivo(),
                personaResponse
        );
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas.",
                    exception
            );
        }

        String email = authentication.getName();

        Instant fechaLimpieza = Instant.now();

        codigos.entrySet().removeIf(
                entrada -> !fechaLimpieza.isBefore(
                        entrada.getValue().caducidad()
                )
        );

        codigos.compute(email, (clave, verificacion) -> {

            Instant ahora = Instant.now();

            int intentos = 0;
            int reenvios = 0;

            if (verificacion != null
                    && ahora.isBefore(verificacion.caducidad())) {

                if (verificacion.intentos() >= maxAttempts) {
                    throw new ResponseStatusException(
                            HttpStatus.TOO_MANY_REQUESTS,
                            "Se ha superado el número máximo de intentos."
                    );
                }

                if (verificacion.reenvios() >= maxResends) {
                    throw new ResponseStatusException(
                            HttpStatus.TOO_MANY_REQUESTS,
                            "Se ha superado el número máximo de reenvíos."
                    );
                }

                if (ahora.isBefore(
                        verificacion.ultimoEnvio().plusSeconds(resendCooldown)
                )) {
                    throw new ResponseStatusException(
                            HttpStatus.TOO_MANY_REQUESTS,
                            "Debes esperar antes de solicitar otro código."
                    );
                }

                intentos = verificacion.intentos();
                reenvios = verificacion.reenvios() + 1;
            }

            String codigo = generarCodigo();
            String hash = passwordEncoder.encode(codigo);

            mailService.enviarCorreo(
                    email,
                    "3VETAS - Código de verificación",
                    "Tu código de verificación es: " + codigo
                            + "\n\nEl código caduca en "
                            + codeExpiration + " segundos."
                            + "\n\nSi no has solicitado este código, "
                            + "ignora este correo electrónico."
            );

            Instant fechaEnvio = Instant.now();

            return new CodigoVerificacion(
                    hash,
                    fechaEnvio.plusSeconds(codeExpiration),
                    intentos,
                    fechaEnvio,
                    reenvios
            );
        });

        return new LoginResponseDTO(true);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO verificarLogin(
            LoginVerificacionRequestDTO request
    ) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> Boolean.TRUE.equals(u.getActivo()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Código de verificación no válido."
                ));

        String email = usuario.getEmail();

        CodigoVerificacion verificacion;

        do {
            verificacion = codigos.get(email);

            if (verificacion == null) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Código de verificación no válido."
                );
            }

            if (!Instant.now().isBefore(verificacion.caducidad())) {

                if (!codigos.remove(email, verificacion)) {
                    continue;
                }

                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "El código de verificación ha caducado."
                );
            }

            if (verificacion.intentos() >= maxAttempts) {
                throw new ResponseStatusException(
                        HttpStatus.TOO_MANY_REQUESTS,
                        "Se ha superado el número máximo de intentos."
                );
            }

            if (!passwordEncoder.matches(
                    request.getCodigo(),
                    verificacion.hash()
            )) {
                CodigoVerificacion actualizada = new CodigoVerificacion(
                        verificacion.hash(),
                        verificacion.caducidad(),
                        verificacion.intentos() + 1,
                        verificacion.ultimoEnvio(),
                        verificacion.reenvios()
                );

                if (!codigos.replace(email, verificacion, actualizada)) {
                    continue;
                }

                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Código de verificación incorrecto."
                );
            }

            if (!Instant.now().isBefore(verificacion.caducidad())) {

                if (!codigos.remove(email, verificacion)) {
                    continue;
                }

                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "El código de verificación ha caducado."
                );
            }

            // Consumir el código para impedir su reutilización.
            if (codigos.remove(email, verificacion)) {
                break;
            }

        } while (true);

        Rol rol = usuario.getRol();

        Persona persona = personaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "No se han encontrado los datos personales del usuario."
                ));

        RolResponseDTO rolResponse = new RolResponseDTO(
                rol.getId(),
                rol.getNombre()
        );

        PersonaResponseDTO personaResponse = new PersonaResponseDTO(
                persona.getId(),
                persona.getNombre(),
                persona.getApellidos(),
                persona.getTelefono(),
                persona.getDni()
        );

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                rolResponse,
                usuario.getActivo(),
                personaResponse
        );
    }

    private String generarCodigo() {
        return String.format(
                Locale.ROOT,
                "%06d",
                secureRandom.nextInt(1_000_000)
        );
    }

    private record CodigoVerificacion(
        String hash,
        Instant caducidad,
        int intentos,
        Instant ultimoEnvio,
        int reenvios
    ) {
    }
}