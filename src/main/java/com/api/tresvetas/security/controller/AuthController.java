package com.api.tresvetas.security.controller;

import com.api.tresvetas.common.dto.ApiResponse;
import com.api.tresvetas.common.factory.ApiResponseFactory;
import com.api.tresvetas.security.service.AuthService;
import com.api.tresvetas.security.service.JwtCookieService;
import com.api.tresvetas.security.service.JwtService;
import com.api.tresvetas.usuarios.dto.request.LoginRequestDTO;
import com.api.tresvetas.usuarios.dto.request.LoginVerificacionRequestDTO;
import com.api.tresvetas.usuarios.dto.request.RegistroRequestDTO;
import com.api.tresvetas.usuarios.dto.response.LoginResponseDTO;
import com.api.tresvetas.usuarios.dto.response.UsuarioResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final JwtCookieService jwtCookieService;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            JwtCookieService jwtCookieService
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.jwtCookieService = jwtCookieService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> register(
            @Valid @RequestBody RegistroRequestDTO request,
            HttpServletRequest httpRequest
    ) {
        UsuarioResponseDTO resultado = authService.register(request);

        return ApiResponseFactory.build(
                HttpStatus.CREATED,
                "Usuario registrado correctamente.",
                resultado,
                httpRequest
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletRequest httpRequest
    ) {
        LoginResponseDTO resultado = authService.login(request);

        return ApiResponseFactory.build(
                HttpStatus.OK,
                "Código de verificación enviado al correo electrónico.",
                resultado,
                httpRequest
        );
    }

    @PostMapping("/login/verify")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> verificarLogin(
            @Valid @RequestBody LoginVerificacionRequestDTO request,
            HttpServletRequest httpRequest
    ) {
        UsuarioResponseDTO resultado = authService.verificarLogin(request);

        String token = jwtService.generateToken(resultado.email());

        ResponseCookie cookie =
                jwtCookieService.createAuthenticationCookie(token);

        ResponseEntity<ApiResponse<UsuarioResponseDTO>> respuesta =
                ApiResponseFactory.build(
                        HttpStatus.OK,
                        "Inicio de sesión completado correctamente.",
                        resultado,
                        httpRequest
                );

        return ResponseEntity.status(respuesta.getStatusCode())
                .headers(respuesta.getHeaders())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(respuesta.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpServletRequest httpRequest
    ) {
        ResponseCookie cookie =
                jwtCookieService.createExpiredAuthenticationCookie();

        ResponseEntity<ApiResponse<Void>> respuesta =
                ApiResponseFactory.build(
                        HttpStatus.OK,
                        "Sesión cerrada correctamente.",
                        null,
                        httpRequest
                );

        return ResponseEntity.status(respuesta.getStatusCode())
                .headers(respuesta.getHeaders())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(respuesta.getBody());
    }
}