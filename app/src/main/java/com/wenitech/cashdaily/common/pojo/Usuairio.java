package com.wenitech.cashdaily.common.pojo;

import com.google.firebase.Timestamp;

public class Usuairio {

    private Timestamp fechaCreacion;
    private String nombreUsuario;
    private String inicialNombre;
    private String tipoCuenta;
    private String suscripcion;

    public Usuairio() {
    }

    public Usuairio(Timestamp fechaCreacion, String nombreUsuario, String inicialNombre, String tipoCuenta, String suscripcion) {
        this.fechaCreacion = fechaCreacion;
        this.nombreUsuario = nombreUsuario;
        this.inicialNombre = inicialNombre;
        this.tipoCuenta = tipoCuenta;
        this.suscripcion = suscripcion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getInicialNombre() {
        return inicialNombre;
    }

    public void setInicialNombre(String inicialNombre) {
        this.inicialNombre = inicialNombre;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(String suscripcion) {
        this.suscripcion = suscripcion;
    }
}
