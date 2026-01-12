package com.ecommerce.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing a vehicle in the inventory.
 * Maps directly to the 'vehiculos' table in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    private Integer id;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precio;
    private String color;
    private String tipo;
    private String estado; 
    private String imagenUrl;
    private String descripcion;
    private Timestamp fechaRegistro;
}