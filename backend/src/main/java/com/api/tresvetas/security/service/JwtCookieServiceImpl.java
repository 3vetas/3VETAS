package com.api.tresvetas.security.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
@Service
public class JwtCookieServiceImpl implements JwtCookieService {

    private final String cookieName;
    private final boolean httpOnly;
    private final boolean secure;
    private final String sameSite;
    private final String path;
    private final long expiration;

    public JwtCookieServiceImpl(
            @Value("${app.security.cookie.name}") String cookieName,
            @Value("${app.security.cookie.http-only}") boolean httpOnly,
            @Value("${app.security.cookie.secure}") boolean secure,
            @Value("${app.security.cookie.same-site}") String sameSite,
            @Value("${app.security.cookie.path}") String path,
            @Value("${app.security.jwt.access-token-expiration}") long expiration
    ) {
        this.cookieName = cookieName;
        this.httpOnly = httpOnly;
        this.secure = secure;
        this.sameSite = sameSite;
        this.path = path;
        this.expiration = expiration;
    }

    @Override
    public ResponseCookie createAuthenticationCookie(String token) {
        return ResponseCookie.from(cookieName, token)
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .path(path)
                .maxAge(Duration.ofSeconds(expiration))
                .build();
    }

    @Override
    public ResponseCookie createExpiredAuthenticationCookie() {
        return ResponseCookie.from(cookieName, "")
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .path(path)
                .maxAge(Duration.ZERO)
                .build();
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);

        return cookie != null ? cookie.getValue() : null;
    }
}