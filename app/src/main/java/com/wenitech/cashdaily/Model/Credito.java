package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Credito {

    private Timestamp fechaPretamo; // 26
    private Timestamp fechaProximaCuota; // 27
    private String modalida; // diario
    private double valorPrestamo;  // 100
    private double porcentaje; // 20
    private double totalPrestamo; //120
    private double deudaPrestamo; //120
    private double numeroCuotas; // 30
    private boolean noCobrarSabados; // false
    private boolean noCobrarDomingos; // false

    public Credito() {
    }

    public Credito(Timestamp fechaPretamo, Timestamp fechaProximaCuota, String modalida, double valorPrestamo, double porcentaje, double totalPrestamo, double deudaPrestamo, double numeroCuotas, boolean noCobrarSabados, boolean noCobrarDomingos) {
        this.fechaPretamo = fechaPretamo;
        this.fechaProximaCuota = fechaProximaCuota;
        this.modalida = modalida;
        this.valorPrestamo = valorPrestamo;
        this.porcentaje = porcentaje;
        this.totalPrestamo = totalPrestamo;
        this.deudaPrestamo = deudaPrestamo;
        this.numeroCuotas = numeroCuotas;
        this.noCobrarSabados = noCobrarSabados;
        this.noCobrarDomingos = noCobrarDomingos;
    }

    public Timestamp getFechaPretamo() {
        return fechaPretamo;
    }

    public void setFechaPretamo(Timestamp fechaPretamo) {
        this.fechaPretamo = fechaPretamo;
    }

    public Timestamp getFechaProximaCuota() {
        return fechaProximaCuota;
    }

    public void setFechaProximaCuota(Timestamp fechaProximaCuota) {
        this.fechaProximaCuota = fechaProximaCuota;
    }

    public String getModalida() {
        return modalida;
    }

    public void setModalida(String modalida) {
        this.modalida = modalida;
    }

    public double getValorPrestamo() {
        return valorPrestamo;
    }

    public void setValorPrestamo(double valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getTotalPrestamo() {
        return totalPrestamo;
    }

    public void setTotalPrestamo(double totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    public double getDeudaPrestamo() {
        return deudaPrestamo;
    }

    public void setDeudaPrestamo(double deudaPrestamo) {
        this.deudaPrestamo = deudaPrestamo;
    }

    public double getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(double numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public boolean isNoCobrarSabados() {
        return noCobrarSabados;
    }

    public void setNoCobrarSabados(boolean noCobrarSabados) {
        this.noCobrarSabados = noCobrarSabados;
    }

    public boolean isNoCobrarDomingos() {
        return noCobrarDomingos;
    }

    public void setNoCobrarDomingos(boolean noCobrarDomingos) {
        this.noCobrarDomingos = noCobrarDomingos;
    }
}
