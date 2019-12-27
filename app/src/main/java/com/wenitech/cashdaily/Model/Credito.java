package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Credito {

    private Timestamp aFechaCreacion;//
    private double bValorPrestamo;
    private int cPorcentaje;//
    private double dTotalPrestamo;//
    private String eModalida;//
    private String fDiaSemanaCobrar;
    private int gDiaQuincenaInicial;
    private int hDiaQuincenaFinal;
    private int iDiaMensual;
    private double jNumeroCuotas;
    private double kValorCuotas;
    private boolean lActivo;

    public Credito() {
    }

    public Credito(Timestamp aFechaCreacion, double bValorPrestamo, int cPorcentaje, double dTotalPrestamo, String eModalida, String fDiaSemanaCobrar, int gDiaQuincenaInicial, int hDiaQuincenaFinal, int iDiaMensual, double jNumeroCuotas, double kValorCuotas, boolean lActivo) {
        this.aFechaCreacion = aFechaCreacion;
        this.bValorPrestamo = bValorPrestamo;
        this.cPorcentaje = cPorcentaje;
        this.dTotalPrestamo = dTotalPrestamo;
        this.eModalida = eModalida;
        this.fDiaSemanaCobrar = fDiaSemanaCobrar;
        this.gDiaQuincenaInicial = gDiaQuincenaInicial;
        this.hDiaQuincenaFinal = hDiaQuincenaFinal;
        this.iDiaMensual = iDiaMensual;
        this.jNumeroCuotas = jNumeroCuotas;
        this.kValorCuotas = kValorCuotas;
        this.lActivo = lActivo;
    }

    public Timestamp getaFechaCreacion() {
        return aFechaCreacion;
    }

    public void setaFechaCreacion(Timestamp aFechaCreacion) {
        this.aFechaCreacion = aFechaCreacion;
    }

    public double getbValorPrestamo() {
        return bValorPrestamo;
    }

    public void setbValorPrestamo(double bValorPrestamo) {
        this.bValorPrestamo = bValorPrestamo;
    }

    public int getcPorcentaje() {
        return cPorcentaje;
    }

    public void setcPorcentaje(int cPorcentaje) {
        this.cPorcentaje = cPorcentaje;
    }

    public double getdTotalPrestamo() {
        return dTotalPrestamo;
    }

    public void setdTotalPrestamo(double dTotalPrestamo) {
        this.dTotalPrestamo = dTotalPrestamo;
    }

    public String geteModalida() {
        return eModalida;
    }

    public void seteModalida(String eModalida) {
        this.eModalida = eModalida;
    }

    public String getfDiaSemanaCobrar() {
        return fDiaSemanaCobrar;
    }

    public void setfDiaSemanaCobrar(String fDiaSemanaCobrar) {
        this.fDiaSemanaCobrar = fDiaSemanaCobrar;
    }

    public int getgDiaQuincenaInicial() {
        return gDiaQuincenaInicial;
    }

    public void setgDiaQuincenaInicial(int gDiaQuincenaInicial) {
        this.gDiaQuincenaInicial = gDiaQuincenaInicial;
    }

    public int gethDiaQuincenaFinal() {
        return hDiaQuincenaFinal;
    }

    public void sethDiaQuincenaFinal(int hDiaQuincenaFinal) {
        this.hDiaQuincenaFinal = hDiaQuincenaFinal;
    }

    public int getiDiaMensual() {
        return iDiaMensual;
    }

    public void setiDiaMensual(int iDiaMensual) {
        this.iDiaMensual = iDiaMensual;
    }

    public double getjNumeroCuotas() {
        return jNumeroCuotas;
    }

    public void setjNumeroCuotas(double jNumeroCuotas) {
        this.jNumeroCuotas = jNumeroCuotas;
    }

    public double getkValorCuotas() {
        return kValorCuotas;
    }

    public void setkValorCuotas(double kValorCuotas) {
        this.kValorCuotas = kValorCuotas;
    }

    public boolean islActivo() {
        return lActivo;
    }

    public void setlActivo(boolean lActivo) {
        this.lActivo = lActivo;
    }
}
