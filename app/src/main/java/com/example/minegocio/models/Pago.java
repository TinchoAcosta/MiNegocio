package com.example.minegocio.models;

import java.util.Date;

public class Pago {
    private int id;
    private Date fecha;
    private double monto;
    private String metodoDePago;
    private int turnoId;
    private Turno turno;

    public Pago() {
    }

    public Pago(int id, Date fecha, double monto, String metodoDePago, int turnoId) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoDePago = metodoDePago;
        this.turnoId = turnoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(int turnoId) {
        this.turnoId = turnoId;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
