package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;


}
