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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository,
            PersonaRepository personaRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
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

        try {
            authenticationManager.authenticate(
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

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "La verificación por correo electrónico todavía no está disponible."
        );
    }


    @Override
    public UsuarioResponseDTO verificarLogin(
            LoginVerificacionRequestDTO request
    ) {
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "La verificación por correo electrónico todavía no está disponible."
        );
    }
}
