package com.api.tresvetas.security.service;

import com.api.tresvetas.usuarios.dto.request.LoginRequestDTO;
import com.api.tresvetas.usuarios.dto.request.LoginVerificacionRequestDTO;
import com.api.tresvetas.usuarios.dto.request.RegistroRequestDTO;
import com.api.tresvetas.usuarios.dto.response.LoginResponseDTO;
import com.api.tresvetas.usuarios.dto.response.UsuarioResponseDTO;

/**
 *
 * @author Jabier Zurro Aduriz
 */
public interface AuthService {

    UsuarioResponseDTO register(RegistroRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    UsuarioResponseDTO verificarLogin(LoginVerificacionRequestDTO request);
}