package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request;

import java.sql.Timestamp;

public record UpdateFlightDto(
    Long id,
    String numeroVuelo,
    String tipoVuelo,
    Long idAeropuertoDestino,
    Long idAeropuertoOrigen,
    Long idTipoAvion,
    Timestamp fechaSalida,
    Timestamp fechaLlegada,
    Double precio,
    Integer cantidadPasajeros,
    Double sobretasa,
    Double porcentajeImpuestos
) {

}
