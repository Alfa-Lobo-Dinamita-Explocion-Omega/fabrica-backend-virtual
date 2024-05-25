package com.udea.edu.co.busquedadevuelos.backendvirtual.domain;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.UserData;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class User implements UserDetails {

    private String id;
    private String identification;
    private String email;
    private String password;
    private String name;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<? extends GrantedAuthority> grantedAuthorities;

    public static User build(UserData userData) {
        Set<GrantedAuthority> authorities = userData.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toSet());

        return User.builder()
                .id(userData.getId())
                .identification(userData.getIdentification())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .name(userData.getName())
                .grantedAuthorities(authorities)
                .active(userData.getActive())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
