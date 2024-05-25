package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.response;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.VueloData;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightsResponse {

    private List<VueloData> vuelos;
    private Long totalItems;

}
