package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Credito {
    private Timestamp fechaCuota;
    private int valorCuota;

    public Credito() {
    }

    public Credito(Timestamp fechaCuota, int valorCuota) {
        this.fechaCuota = fechaCuota;
        this.valorCuota = valorCuota;
    }

    public Timestamp getFechaCuota() {
        return fechaCuota;
    }

    public void setFechaCuota(Timestamp fechaCuota) {
        this.fechaCuota = fechaCuota;
    }

    public int getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(int valorCuota) {
        this.valorCuota = valorCuota;
    }
}
