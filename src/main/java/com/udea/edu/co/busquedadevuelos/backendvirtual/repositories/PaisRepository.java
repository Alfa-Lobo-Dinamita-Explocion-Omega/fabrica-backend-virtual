package com.udea.edu.co.busquedadevuelos.backendvirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.Pais;


@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

}
