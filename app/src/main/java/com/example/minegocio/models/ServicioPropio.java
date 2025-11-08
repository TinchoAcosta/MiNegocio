package com.example.minegocio.models;

import java.util.ArrayList;
import java.util.List;

public class ServicioPropio {
    private int id;
    private int duracionMinutos;
    private double precioBase;
    private int servicioId;
    private int usuarioId;
    private Usuario usuario;
    private ServicioBase servicioBase;
    private List<Promo> promos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();

    public ServicioPropio() {
    }

    public ServicioPropio(int id, int duracionMinutos, double precioBase, int servicioId, int usuarioId) {
        this.id = id;
        this.duracionMinutos = duracionMinutos;
        this.precioBase = precioBase;
        this.servicioId = servicioId;
        this.usuarioId = usuarioId;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public ServicioBase getServicioBase() {
        return servicioBase;
    }

    public void setServicioBase(ServicioBase servicioBase) {
        this.servicioBase = servicioBase;
    }

    public List<Promo> getPromos() {
        return promos;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
