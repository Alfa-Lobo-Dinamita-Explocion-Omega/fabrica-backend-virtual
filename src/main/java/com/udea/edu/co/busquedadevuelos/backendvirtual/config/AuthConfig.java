package com.udea.edu.co.busquedadevuelos.backendvirtual.config;

import com.udea.edu.co.busquedadevuelos.backendvirtual.config.jwt.JwtProvider;
import com.udea.edu.co.busquedadevuelos.backendvirtual.mappers.UserMapper;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.AuthService;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.AuthServiceImpl;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    @Bean
    public JwtProvider jwtProvider(JwtProperties jwtProperties) {
        return JwtProvider.builder()
                .jwtProperties(jwtProperties)
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthUtils authUtils(AuthenticationManagerBuilder authManagerBuilder, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        return AuthUtils.builder()
                .authManagerBuilder(authManagerBuilder)
                .jwtProvider(jwtProvider)
                .passwordEncoder(passwordEncoder)
                .build();
    }

    @Bean
    public AuthService authService(AuthUtils authUtils,
                                   UserService userService) {
        return AuthServiceImpl.builder()
                .userService(userService)
                .authUtils(authUtils)
                .build();
    }

}
