package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.Aeropuerto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.AeropuertoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/aeropuertos")
public class AeropuertosController {
    

    private final AeropuertoService aeropuertoService;

    
    public AeropuertosController(AeropuertoService aeropuertoService) {
        this.aeropuertoService = aeropuertoService;
    }


    @GetMapping("listar")
    public ResponseEntity<List<Object>> getAirports() {
        var aeropuertos = aeropuertoService.findAeropuerto();
        return ResponseEntity.ok(aeropuertos);
    }
    
    @GetMapping("aeropuertosCiudad")
    public ResponseEntity<List<Aeropuerto>> getMethodName(@RequestParam String nombre) {
        var aeropuertos = aeropuertoService.findAeropuertoByNombre(nombre);
        return ResponseEntity.ok(aeropuertos);
    }
    
    
}
