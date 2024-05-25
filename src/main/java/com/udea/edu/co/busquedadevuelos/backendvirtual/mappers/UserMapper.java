package com.udea.edu.co.busquedadevuelos.backendvirtual.mappers;

import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.User;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.UserData;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserData toData(User user) {
        return UserData.builder()
                .id(user.getId())
                .identification(user.getIdentification())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toEntity(UserData userData) {
        return User.builder()
                .id(userData.getId())
                .identification(userData.getIdentification())
                .email(userData.getEmail())
                .password(userData.getPassword())
                .name(userData.getName())
                .grantedAuthorities(userData.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                        .collect(Collectors.toSet()))
                .active(userData.getActive())
                .createdAt(userData.getCreatedAt())
                .build();
    }
}
