package com.udea.edu.co.busquedadevuelos.backendvirtual.repositories;

import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Role;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.RoleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleData, Long> {
    Optional<RoleData> findByRole(Role roleName);
}
