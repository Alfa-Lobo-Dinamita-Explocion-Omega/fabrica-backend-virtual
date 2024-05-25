package com.udea.edu.co.busquedadevuelos.backendvirtual.config;

import com.udea.edu.co.busquedadevuelos.backendvirtual.config.jwt.JwtProvider;
import lombok.Builder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder(toBuilder = true)
public record AuthUtils(AuthenticationManagerBuilder authManagerBuilder,
                        JwtProvider jwtProvider,
                        PasswordEncoder passwordEncoder) {
}
