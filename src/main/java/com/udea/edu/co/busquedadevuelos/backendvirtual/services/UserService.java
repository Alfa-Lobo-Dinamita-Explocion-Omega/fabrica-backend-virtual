package com.udea.edu.co.busquedadevuelos.backendvirtual.services;

import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    User save(User entity);

}
