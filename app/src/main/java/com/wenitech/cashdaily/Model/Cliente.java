package com.wenitech.cashdaily.Model;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.local.ReferenceSet;
import com.google.firebase.firestore.model.value.ReferenceValue;

public class Cliente {

    private Timestamp fechaCreacion;
    private String documentReference;
    private String identificacion;
    private String nombre;
    private String inicialNombre;
    private String telefono;
    private String ubicacion;
    private int valorPrestamo;
    private int deudaPrestamo;
    private Boolean estado;


    public Cliente() {
    }

    public Cliente(Timestamp fechaCreacion, String documentReference, String identificacion, String nombre, String inicialNombre, String telefono, String ubicacion, int valorPrestamo, int deudaPrestamo, Boolean estado) {
        this.fechaCreacion = fechaCreacion;
        this.documentReference = documentReference;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.inicialNombre = inicialNombre;
        this.telefono = telefono;
        this.ubicacion = ubicacion;
        this.valorPrestamo = valorPrestamo;
        this.deudaPrestamo = deudaPrestamo;
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicialNombre() {
        return inicialNombre;
    }

    public void setInicialNombre(String inicialNombre) {
        this.inicialNombre = inicialNombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getValorPrestamo() {
        return valorPrestamo;
    }

    public void setValorPrestamo(int valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public int getDeudaPrestamo() {
        return deudaPrestamo;
    }

    public void setDeudaPrestamo(int deudaPrestamo) {
        this.deudaPrestamo = deudaPrestamo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
