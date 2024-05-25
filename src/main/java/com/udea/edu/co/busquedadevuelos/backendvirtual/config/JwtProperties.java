package com.udea.edu.co.busquedadevuelos.backendvirtual.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtProperties {

    private String secretKey;
    private ExpirationProperties accessToken;
    private ExpirationProperties idToken;

    @Setter
    @Getter
    public static class ExpirationProperties {
        private Long expiration;
    }
}
