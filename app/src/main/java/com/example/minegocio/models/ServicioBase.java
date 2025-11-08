package com.example.minegocio.models;

public class ServicioBase {
    private int id;
    private String detalle;
    private String categoria;

    public ServicioBase() {
    }

    public ServicioBase(int id, String detalle, String categoria) {
        this.id = id;
        this.detalle = detalle;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
