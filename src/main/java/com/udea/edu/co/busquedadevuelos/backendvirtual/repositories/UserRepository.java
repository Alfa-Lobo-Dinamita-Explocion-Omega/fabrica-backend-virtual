package com.udea.edu.co.busquedadevuelos.backendvirtual.repositories;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, String> {
    Optional<UserData> findByEmail(String email);

    Optional<UserData> findOneById(String email);

}
