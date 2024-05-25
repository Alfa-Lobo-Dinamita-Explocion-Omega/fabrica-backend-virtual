package com.udea.edu.co.busquedadevuelos.backendvirtual.domain;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private String accessToken;
    private String idToken;
}
