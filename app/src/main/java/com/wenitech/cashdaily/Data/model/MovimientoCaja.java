package com.wenitech.cashdaily.Data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class MovimientoCaja {
    
    private String id;
    private Double valor;
    private String Descripcion;
    private Timestamp fecha;
    private Boolean agregarDinero;

    public MovimientoCaja() {
    }

    public MovimientoCaja(String id, Double valor, String descripcion, Timestamp fecha, Boolean agregarDinero) {
        this.id = id;
        this.valor = valor;
        Descripcion = descripcion;
        this.fecha = fecha;
        this.agregarDinero = agregarDinero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Boolean getAgregarDinero() {
        return agregarDinero;
    }

    public void setAgregarDinero(Boolean agregarDinero) {
        this.agregarDinero = agregarDinero;
    }
}
