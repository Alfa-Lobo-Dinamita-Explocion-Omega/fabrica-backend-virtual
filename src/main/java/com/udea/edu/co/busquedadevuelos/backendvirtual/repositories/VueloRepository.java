package com.udea.edu.co.busquedadevuelos.backendvirtual.repositories;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.VueloData;

@Repository
public interface VueloRepository extends JpaRepository<VueloData, Long> {

    List<VueloData> findByFechaLlegada(LocalDateTime fechaLlegada, Pageable pageable);

    Optional<VueloData> findByNumeroVuelo(String numeroVuelo);


    
}
