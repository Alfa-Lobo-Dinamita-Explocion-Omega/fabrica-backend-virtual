package com.udea.edu.co.busquedadevuelos.backendvirtual.mappers;

import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Role;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.RoleData;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleData toModel(Role entity) {
        return RoleData.builder()
                .role(entity)
                .build();
    }

    public Role toEntity(RoleData model) {
        return model.getRole();
    }

}
