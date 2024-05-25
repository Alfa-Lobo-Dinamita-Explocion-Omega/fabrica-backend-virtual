package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request;

import com.udea.edu.co.busquedadevuelos.backendvirtual.entities.Aeropuerto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlightDto {

    // @NotEmpty(message = "El número de vuelo no puede estar vacío")
    private String numeroVuelo;

    // @NotEmpty(message = "El tipo de vuelo no puede estar vacío")
    private String tipoVuelo;

    // @NotNull(message = "El id del aeropuerto destino no puede estar vacío")
    private Long idAeropuertoDestino;

    private Long idAeropuertoOrigen;
    private Long idTipoAvion;

    private Timestamp fechaSalida;
    private Timestamp fechaLlegada;
    private Double precio;
    private Integer cantidadPasajeros;
    private Double sobretasa;
    private Double porcentajeImpuestos;
//    private Aeropuerto destino;
//    private Aeropuerto origen;

}
