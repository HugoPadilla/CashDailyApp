package com.wenitech.cashdaily.Model;

public class Gasto {

    private String valor;
    private String descripcion;
    private String fecha;

    public Gasto() {
    }

    public Gasto(String valor, String descripcion, String fecha) {
        this.valor = valor;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
