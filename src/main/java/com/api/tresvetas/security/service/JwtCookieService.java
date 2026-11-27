package com.api.tresvetas.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
public interface JwtCookieService {

    ResponseCookie createAuthenticationCookie(String token);

    ResponseCookie createExpiredAuthenticationCookie();

    String extractToken(HttpServletRequest request);
}
