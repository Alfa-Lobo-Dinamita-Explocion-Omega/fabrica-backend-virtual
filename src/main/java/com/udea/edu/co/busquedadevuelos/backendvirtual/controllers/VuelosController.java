package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers;

import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.CreateFlightDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.response.FlightsResponse;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.VuelosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.VueloData;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(path = "/api/v1/vuelos")
@RequiredArgsConstructor
public class VuelosController {
    
    private final VuelosService vuelosService;

    @PostMapping("")
    public ResponseEntity<VueloData> createFlight(@RequestBody CreateFlightDto createFlightDto) {
        this.vuelosService.createFlight(createFlightDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<FlightsResponse> getAllFlights(
            @RequestParam(required = false, defaultValue = "0") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ){
        var vuelos = this.vuelosService.getAllFlights(page, size);
        return ResponseEntity.ok(vuelos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VueloData> getFlightById(@PathVariable Long id) {
        Optional<VueloData> flightOptional = this.vuelosService.getFlightById(id);
        return flightOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ida-vuelta")
    public ResponseEntity<List<VueloData>> getRoundTripFlights(
            @RequestParam(required = false, defaultValue = "0") Long page,
            @RequestParam(required = false, defaultValue = "10") Long size
    ) {
        List<VueloData> vueloData = this.vuelosService.listarVuelosIdaVuelta(page, size);
        return ResponseEntity.ok(vueloData);
    }


}