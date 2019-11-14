package com.wenitech.cashdaily.Model;

public class Usuairio {

    private String nombreUsuario;
    private int totalEfectivo,cobradoHoy,esteMes;

    public Usuairio() {
    }

    public Usuairio(String nombreUsuario, int totalEfectivo, int cobradoHoy, int esteMes) {
        this.nombreUsuario = nombreUsuario;
        this.totalEfectivo = totalEfectivo;
        this.cobradoHoy = cobradoHoy;
        this.esteMes = esteMes;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getTotalEfectivo() {
        return totalEfectivo;
    }

    public void setTotalEfectivo(int totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    public int getCobradoHoy() {
        return cobradoHoy;
    }

    public void setCobradoHoy(int cobradoHoy) {
        this.cobradoHoy = cobradoHoy;
    }

    public int getEsteMes() {
        return esteMes;
    }

    public void setEsteMes(int esteMes) {
        this.esteMes = esteMes;
    }
}
