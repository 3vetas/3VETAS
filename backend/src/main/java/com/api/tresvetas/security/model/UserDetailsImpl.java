package com.api.tresvetas.security.model;

import com.api.tresvetas.usuarios.entity.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 *
 * @author Jabier Zurro Aduriz
 */
public final class UserDetailsImpl implements UserDetails {

    private final Integer id;
    private final String email;
    private final String password;
    private final boolean activo;
    private final Collection<? extends GrantedAuthority> authorities;

    private UserDetailsImpl(
            Integer id,
            String email,
            String password,
            boolean activo,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.activo = activo;
        this.authorities = authorities;
    }

    public static UserDetailsImpl from(Usuario usuario) {
        String nombreRol = usuario.getRol().getNombre();

        return new UserDetailsImpl(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPasswordHash(),
                Boolean.TRUE.equals(usuario.getActivo()),
                List.of(new SimpleGrantedAuthority("ROLE_" + nombreRol))
        );
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}