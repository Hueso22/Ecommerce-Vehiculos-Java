package com.ecommerce.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    private boolean eliminado; // NUEVO CAMPO: Bandera de borrado lógico

    // Constructor Vacío
    public Vehiculo() {
    }

    // Constructor Completo (Actualizado)
    public Vehiculo(Integer id, String marca, String modelo, Integer anio, BigDecimal precio, String color, String tipo, String estado, String imagenUrl, String descripcion, Timestamp fechaRegistro, boolean eliminado) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.color = color;
        this.tipo = tipo;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.eliminado = eliminado;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
}