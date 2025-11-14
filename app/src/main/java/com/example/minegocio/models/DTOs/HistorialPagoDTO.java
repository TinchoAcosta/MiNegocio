package com.example.minegocio.models.DTOs;

import java.util.Date;

public class HistorialPagoDTO {

    private int id;
    private Date fechaPago;
    private double monto;
    private String metodoDePago;

    // Cliente
    private String clienteNombre;
    private String clienteApellido;

    // Turno
    private Date fechaTurno;

    // Servicio
    private String servicioDetalle;

    // Promo opcional
    private String promoDescripcion;

    public HistorialPagoDTO() {
    }

    public HistorialPagoDTO(int id, Date fechaPago, double monto, String metodoDePago,
                            String clienteNombre, String clienteApellido,
                            Date fechaTurno, String servicioDetalle, String promoDescripcion) {

        this.id = id;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoDePago = metodoDePago;
        this.clienteNombre = clienteNombre;
        this.clienteApellido = clienteApellido;
        this.fechaTurno = fechaTurno;
        this.servicioDetalle = servicioDetalle;
        this.promoDescripcion = promoDescripcion;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoDePago() { return metodoDePago; }
    public void setMetodoDePago(String metodoDePago) { this.metodoDePago = metodoDePago; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteApellido() { return clienteApellido; }
    public void setClienteApellido(String clienteApellido) { this.clienteApellido = clienteApellido; }

    public Date getFechaTurno() { return fechaTurno; }
    public void setFechaTurno(Date fechaTurno) { this.fechaTurno = fechaTurno; }

    public String getServicioDetalle() { return servicioDetalle; }
    public void setServicioDetalle(String servicioDetalle) { this.servicioDetalle = servicioDetalle; }

    public String getPromoDescripcion() { return promoDescripcion; }
    public void setPromoDescripcion(String promoDescripcion) { this.promoDescripcion = promoDescripcion; }
}

