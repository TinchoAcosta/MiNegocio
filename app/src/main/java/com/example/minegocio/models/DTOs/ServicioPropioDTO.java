package com.example.minegocio.models.DTOs;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ServicioPropioDTO implements Serializable {
    private int id;
    private double precioBase;
    private int duracionMinutos;
    private String detalle;
    private String categoria;

    public ServicioPropioDTO(){}

    public ServicioPropioDTO(String categoria, double precioBase, int id, int duracionMinutos, String detalle) {
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.id = id;
        this.duracionMinutos = duracionMinutos;
        this.detalle = detalle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @NonNull
    @Override
    public String toString() {
        return categoria+" - "+detalle;
    }
}
