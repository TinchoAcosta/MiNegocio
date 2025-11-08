package com.example.minegocio.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Turno {
    private int id;
    private Date fecha;
    private String descripcion;
    private EstadoTurno estado;
    private int clienteId;
    private int servicioId;
    private int promoId;
    private Cliente cliente;
    private ServicioPropio servicio;
    private Promo promo;
    private List<Pago> pagos = new ArrayList<>();

    public Turno() {
    }

    public Turno(int id, Date fecha, String descripcion, EstadoTurno estado, int clienteId, int servicioId, int promoId) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
        this.clienteId = clienteId;
        this.servicioId = servicioId;
        this.promoId = promoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public ServicioPropio getServicio() {
        return servicio;
    }

    public void setServicio(ServicioPropio servicio) {
        this.servicio = servicio;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}
