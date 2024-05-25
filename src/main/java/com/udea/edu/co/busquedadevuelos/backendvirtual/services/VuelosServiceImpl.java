package com.udea.edu.co.busquedadevuelos.backendvirtual.services;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.CreateFlightDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.response.FlightsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.udea.edu.co.busquedadevuelos.backendvirtual.repositories.VueloRepository;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.VueloData;

@Service
@RequiredArgsConstructor
public class VuelosServiceImpl implements VuelosService {

    private final VueloRepository vueloRepository;

    @Override
    public FlightsResponse getAllFlights(Long page, Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<VueloData> result = vueloRepository.findAll(pageable);
        return FlightsResponse.builder()
                .vuelos(result.getContent())
                .totalItems(result.getTotalElements())
                .build();
    }

    @Override
    public Optional<VueloData> getFlightById(Long id) {
        return vueloRepository.findById(id);
    }

    @Override
    public List<VueloData> listarVuelosIdaVuelta(Long page, Long size) {
        // Implementa la lógica para obtener los vuelos de ida y vuelta
        // Por ejemplo, podrías filtrar los vuelos por fecha de llegada igual a la fecha de salida más un día
        LocalDateTime fechaLlegada = LocalDateTime.now().plusDays(1); // Suponiendo que hoy es la fecha de salida
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        return vueloRepository.findByFechaLlegada(fechaLlegada, pageable);
    }

    @Override
    public void createFlight(CreateFlightDto createFlightDto) {
        VueloData vueloData = VueloData.builder()
                .fechaSalida(createFlightDto.getFechaSalida())
                .fechaLlegada(createFlightDto.getFechaLlegada())
                .tipoVuelo(createFlightDto.getTipoVuelo())
                .numeroVuelo(createFlightDto.getNumeroVuelo())
                .idAeropuertoDestino(createFlightDto.getIdAeropuertoDestino())
                .idAeropuertoOrigen(createFlightDto.getIdAeropuertoOrigen())
                .idTipoAvion(createFlightDto.getIdTipoAvion())
                .precio(createFlightDto.getPrecio())
                .cantidadPasajeros(createFlightDto.getCantidadPasajeros())
                .sobretasa(createFlightDto.getSobretasa())
                .porcentajeImpuestos(createFlightDto.getPorcentajeImpuestos())
                .build();
        vueloRepository.save(vueloData);
    }

}

