package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Cuota {
    private Timestamp bFechaCreacion;
    private int cValorCuota;

    public Cuota() {
    }

    public Cuota(Timestamp bFechaCreacion, int cValorCuota) {
        this.bFechaCreacion = bFechaCreacion;
        this.cValorCuota = cValorCuota;
    }

    public Timestamp getbFechaCreacion() {
        return bFechaCreacion;
    }

    public void setbFechaCreacion(Timestamp bFechaCreacion) {
        this.bFechaCreacion = bFechaCreacion;
    }

    public int getcValorCuota() {
        return cValorCuota;
    }

    public void setcValorCuota(int cValorCuota) {
        this.cValorCuota = cValorCuota;
    }
}
