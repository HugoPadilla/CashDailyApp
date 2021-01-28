package com.wenitech.cashdaily.Data.model;

public class Caja {

    private Double totalCaja;
    private Double Gastos;
    

    public Caja() {
    }

    public Caja(Double totalCaja) {
        this.totalCaja = totalCaja;
    }

    public Double getTotalCaja() {
        return totalCaja;
    }

    public void setTotalCaja(Double totalCaja) {
        this.totalCaja = totalCaja;
    }
}
