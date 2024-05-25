package com.udea.edu.co.busquedadevuelos.backendvirtual.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.Aeropuerto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.repositories.AeropuertoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AeropuertoService {
    
    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository){
        this.aeropuertoRepository = aeropuertoRepository;
    }

    public List<Object> findAeropuerto() {
        return aeropuertoRepository.listAeropuertos();
    }

    public List<Aeropuerto> findAeropuertoByNombre(String nombre) {
        List<Aeropuerto> aeropuertos = aeropuertoRepository.findAll();
        List<Aeropuerto> listAeropuertos = new ArrayList<>();
        for (Aeropuerto aeropuerto : aeropuertos) {
            if(aeropuerto.getNombre().equals(nombre)){
                listAeropuertos.add(aeropuerto);
            }
        }
        return listAeropuertos;
    }

}
