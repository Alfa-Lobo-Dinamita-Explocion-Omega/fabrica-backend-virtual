package com.udea.edu.co.busquedadevuelos.backendvirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    
}
