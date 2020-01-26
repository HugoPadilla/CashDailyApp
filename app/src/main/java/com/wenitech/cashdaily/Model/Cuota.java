package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Cuota {

    private Timestamp fechaCreacion;
    private String nombreCreacion;
    private String estadoEditado;
    private double valorCuota;

    public Cuota() {
    }

    public Cuota(Timestamp fechaCreacion, String nombreCreacion, String estadoEditado, double valorCuota) {
        this.fechaCreacion = fechaCreacion;
        this.nombreCreacion = nombreCreacion;
        this.estadoEditado = estadoEditado;
        this.valorCuota = valorCuota;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreCreacion() {
        return nombreCreacion;
    }

    public void setNombreCreacion(String nombreCreacion) {
        this.nombreCreacion = nombreCreacion;
    }

    public String getEstadoEditado() {
        return estadoEditado;
    }

    public void setEstadoEditado(String estadoEditado) {
        this.estadoEditado = estadoEditado;
    }

    public double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(double valorCuota) {
        this.valorCuota = valorCuota;
    }
}
