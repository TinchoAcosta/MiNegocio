package com.example.minegocio.models;

public class Pago {
    private int id;
    private String fecha;
    private double monto;
    private String metodoDePago;
    private int turnoId;
    private Turno turno;

    public Pago() {
    }

    public Pago(int id, String fecha, double monto, String metodoDePago, int turnoId) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoDePago = metodoDePago;
        this.turnoId = turnoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
