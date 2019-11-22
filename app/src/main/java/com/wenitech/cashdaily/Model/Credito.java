package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Credito {

    private Timestamp aFechaCreacion;
    private String bModalida;
    private int cNumeroCuotas;
    private int dValorCuotas;
    private int eValorPrestamo;
    private int fDeudaPrestamo;
    private boolean gActivo;

    public Credito(Timestamp aFechaCreacion, String bModalida, int cNumeroCuotas,
                   int dValorCuotas, int eValorPrestamo, int fDeudaPrestamo, boolean gActivo) {
        this.aFechaCreacion = aFechaCreacion;
        this.bModalida = bModalida;
        this.cNumeroCuotas = cNumeroCuotas;
        this.dValorCuotas = dValorCuotas;
        this.eValorPrestamo = eValorPrestamo;
        this.fDeudaPrestamo = fDeudaPrestamo;
        this.gActivo = gActivo;
    }

    public Timestamp getaFechaCreacion() {
        return aFechaCreacion;
    }

    public void setaFechaCreacion(Timestamp aFechaCreacion) {
        this.aFechaCreacion = aFechaCreacion;
    }

    public String getbModalida() {
        return bModalida;
    }

    public void setbModalida(String bModalida) {
        this.bModalida = bModalida;
    }

    public int getcNumeroCuotas() {
        return cNumeroCuotas;
    }

    public void setcNumeroCuotas(int cNumeroCuotas) {
        this.cNumeroCuotas = cNumeroCuotas;
    }

    public int getdValorCuotas() {
        return dValorCuotas;
    }

    public void setdValorCuotas(int dValorCuotas) {
        this.dValorCuotas = dValorCuotas;
    }

    public int geteValorPrestamo() {
        return eValorPrestamo;
    }

    public void seteValorPrestamo(int eValorPrestamo) {
        this.eValorPrestamo = eValorPrestamo;
    }

    public int getfDeudaPrestamo() {
        return fDeudaPrestamo;
    }

    public void setfDeudaPrestamo(int fDeudaPrestamo) {
        this.fDeudaPrestamo = fDeudaPrestamo;
    }

    public boolean isgActivo() {
        return gActivo;
    }

    public void setgActivo(boolean gActivo) {
        this.gActivo = gActivo;
    }
}
