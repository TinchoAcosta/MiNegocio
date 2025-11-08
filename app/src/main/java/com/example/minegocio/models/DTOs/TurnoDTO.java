package com.example.minegocio.models.DTOs;

import java.util.List;

public class TurnoDTO {
    private int id;
    private String fecha;
    private String descripcion;
    private String estado;
    private String clienteNombre;
    private String servicioNombre;
    private String categoria;
    private double precioBase;
    private int clienteId;
    private String promoDescripcion;
    private Double precioPromo; // puede ser null
    private List<PagoDTO> pagos;

    // Getters y setters
    public int getId() { return id; }
    public String getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public String getClienteNombre() { return clienteNombre; }
    public String getServicioNombre() { return servicioNombre; }
    public String getCategoria() { return categoria; }
    public double getPrecioBase() { return precioBase; }
    public String getPromoDescripcion() { return promoDescripcion; }
    public Double getPrecioPromo() { return precioPromo; }
    public int getClienteId() {return clienteId; }
    public List<PagoDTO> getPagos() { return pagos; }
}

