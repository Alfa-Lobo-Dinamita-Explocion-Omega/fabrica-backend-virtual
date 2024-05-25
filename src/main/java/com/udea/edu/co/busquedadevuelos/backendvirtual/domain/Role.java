package com.udea.edu.co.busquedadevuelos.backendvirtual.domain;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("ADMINISTRADOR"),
    ROLE_USER("USUARIO");

    public final String label;
    Role(String label) {
        this.label = label;
    }

}
