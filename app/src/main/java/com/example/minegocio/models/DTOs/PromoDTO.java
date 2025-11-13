package com.example.minegocio.models.DTOs;

public class PromoDTO {
    private int id;
    private String descripcion;
    private String imagen;
    private Double precioNuevo; // nullable
    private String condicion;
    private String fechaFin; // o Date si quieres parsear fechas
    private ServicioPropioDTO servicioPropio;

    public PromoDTO() {}

    public PromoDTO(ServicioPropioDTO servicioPropio, Double precioNuevo, String imagen, int id, String fechaFin, String descripcion, String condicion) {
        this.servicioPropio = servicioPropio;
        this.precioNuevo = precioNuevo;
        this.imagen = imagen;
        this.id = id;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.condicion = condicion;
    }

    public Double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(Double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ServicioPropioDTO getServicioPropio() {
        return servicioPropio;
    }

    public void setServicioPropio(ServicioPropioDTO servicioPropio) {
        this.servicioPropio = servicioPropio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}