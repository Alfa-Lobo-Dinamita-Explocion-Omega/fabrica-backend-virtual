package com.udea.edu.co.busquedadevuelos.backendvirtual.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "aeropuertos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Aeropuerto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "id_ciudad", nullable = false)
    private Long idCiudad;

    @ManyToOne
    @JoinColumn(name = "id_ciudad", insertable = false, updatable = false)
    private Ciudad ciudad;
    
}
