package com.wenitech.cashdaily.Model;


import com.google.firebase.Timestamp;

public class Cliente {

    private Timestamp fechaCreacion;//
    private String nombreCliente;//
    private String identificacion;
    private String inicialNombre;
    private String genero;
    private String telefono;
    private String ciudad;
    private String direccion;

    public Cliente() {
    }

    public Cliente(Timestamp fechaCreacion, String nombreCliente, String identificacion, String inicialNombre, String genero, String telefono, String ciudad, String direccion) {
        this.fechaCreacion = fechaCreacion;
        this.nombreCliente = nombreCliente;
        this.identificacion = identificacion;
        this.inicialNombre = inicialNombre;
        this.genero = genero;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getInicialNombre() {
        return inicialNombre;
    }

    public void setInicialNombre(String inicialNombre) {
        this.inicialNombre = inicialNombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
