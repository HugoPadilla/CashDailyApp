package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Gasto {

    private int valor;
    private String descripcion;
    private Timestamp fecha;

    public Gasto() {
    }

    public Gasto(int valor, String descripcion, Timestamp fecha) {
        this.valor = valor;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
