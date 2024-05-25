package com.udea.edu.co.busquedadevuelos.backendvirtual;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.CreateFlightDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.response.FlightsResponse;
import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.VueloData;
import com.udea.edu.co.busquedadevuelos.backendvirtual.repositories.VueloRepository;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.VuelosServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class VuelosServiceTest {

    @Mock
    private VueloRepository vueloRepository;

    @InjectMocks
    private VuelosServiceImpl vuelosService;

    @Test
    public void testCreateFlight_Success() {
        // Datos válidos para el test
        CreateFlightDto createFlightDto = new CreateFlightDto(
                "FL1234", "Doméstico", 1L, 2L, 3L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis() + 3600000), // +1 hora
                199.99, 100, 10.0, 15.0
        );

        // Llamar al método
        vuelosService.createFlight(createFlightDto);

        // Verificar que el método save fue llamado
        verify(vueloRepository, times(1)).save(any(VueloData.class));
    }


    @Test
    public void testCreateFlight_RepositoryException() {
        // Simular que el repositorio lanza una excepción
        doThrow(new RuntimeException("Error de base de datos")).when(vueloRepository).save(any(VueloData.class));

        CreateFlightDto createFlightDto = new CreateFlightDto(
                "FL1234", "Doméstico", 1L, 2L, 3L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis() + 3600000),
                199.99, 100, 10.0, 15.0
        );

        // Verificar que el método lanza una excepción
        assertThrows(RuntimeException.class, () -> {
            vuelosService.createFlight(createFlightDto);
        });

        // Verificar que el método save fue llamado antes de lanzar la excepción
        verify(vueloRepository, times(1)).save(any(VueloData.class));
    }

    @Test
    public void testGetAllFlights() {
        // Datos simulados
        VueloData vuelo1 = new VueloData();
        vuelo1.setNumeroVuelo("FL1234");

        VueloData vuelo2 = new VueloData();
        vuelo2.setNumeroVuelo("FL5678");

        List<VueloData> vuelosSimulados = Arrays.asList(vuelo1, vuelo2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<VueloData> pageResult = new PageImpl<>(vuelosSimulados, pageable, vuelosSimulados.size());

        // Simulación del repositorio
        when(vueloRepository.findAll(pageable)).thenReturn(pageResult);

        // Llamada al método
        FlightsResponse response = vuelosService.getAllFlights(0L, 10L);

        // Verificación de resultados
        assertEquals(2, response.getVuelos().size()); // Verifica el tamaño de la lista
        assertEquals("FL1234", response.getVuelos().get(0).getNumeroVuelo()); // Verifica el primer vuelo
        assertEquals("FL5678", response.getVuelos().get(1).getNumeroVuelo()); // Verifica el segundo vuelo
        assertEquals(2L, response.getTotalItems()); // Verifica el total de elementos

        // Verificar que el repositorio fue llamado correctamente
        verify(vueloRepository, times(1)).findAll(pageable); // Verifica que el repositorio fue llamado
    }



}