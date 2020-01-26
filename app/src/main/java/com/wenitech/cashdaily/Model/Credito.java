package com.wenitech.cashdaily.Model;

import com.google.firebase.Timestamp;

public class Credito {

    private Timestamp fechaPretamo;
    private String modalida;
    private double valorPrestamo;
    private double porcentaje;
    private double totalPrestamo;
    private double deudaPrestamo;
    private double numeroCuotas;
    private boolean noCobrarSabados;
    private boolean noCobrarDomingos;

    public Credito() {
    }

}
