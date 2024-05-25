package com.udea.edu.co.busquedadevuelos.backendvirtual.entities;

import java.sql.Timestamp;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vuelos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VueloData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_vuelo", nullable = false)
    private String numeroVuelo;

    @Column(name = "tipo_vuelo", nullable = false)
    private String tipoVuelo;

    @Column(name = "id_aeropuerto_destino", nullable = false)
    private Long idAeropuertoDestino;

    @Column(name = "id_aeropuerto_origen", nullable = false)
    private Long idAeropuertoOrigen;

    @Column(name = "id_tipo_avion", nullable = false)
    private Long idTipoAvion;

    @Column(name = "fecha_salida")
    private Timestamp fechaSalida;

    @Column(name = "fecha_llegada")
    private Timestamp fechaLlegada;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "cantidad_pasajeros", nullable = false)
    private Integer cantidadPasajeros;

    @Column(name = "sobretasa", nullable = false)
    private Double sobretasa;

    @Column(name = "porcentaje_impuestos", nullable = false)
    private Double porcentajeImpuestos;

//    @ManyToOne
//    @JoinColumn(name = "id_aeropuerto_destino", insertable = false, updatable = false)
//    private Aeropuerto destino;
//
//    @ManyToOne
//    @JoinColumn(name = "id_aeropuerto_origen", insertable = false, updatable = false)
//    private Aeropuerto origen;

}