package com.example.minegocio.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Promo {
    private int id;
    private String descripcion;
    private Date fechaFin;
    private String imagen;
    private Double precioNuevo; // C#: decimal? → Java: Double (nullable)
    private String condicion;
    private int servicioPropioId;
    private ServicioPropio servicioPropio;
    private List<Turno> turnos = new ArrayList<>();

    public Promo() {
    }

    public Promo(int id, String descripcion, Date fechaFin, String imagen, Double precioNuevo, String condicion, int servicioPropioId) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.imagen = imagen;
        this.precioNuevo = precioNuevo;
        this.condicion = condicion;
        this.servicioPropioId = servicioPropioId;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(Double precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public ServicioPropio getServicioPropio() {
        return servicioPropio;
    }

    public void setServicioPropio(ServicioPropio servicioPropio) {
        this.servicioPropio = servicioPropio;
    }

    public int getServicioPropioId() {
        return servicioPropioId;
    }

    public void setServicioPropioId(int servicioPropioId) {
        this.servicioPropioId = servicioPropioId;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
}
