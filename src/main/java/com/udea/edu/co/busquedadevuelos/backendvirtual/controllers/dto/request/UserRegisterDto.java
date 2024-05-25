package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Identification cannot be null")
    private String identification;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Name cannot be null")
    private String password;




}
