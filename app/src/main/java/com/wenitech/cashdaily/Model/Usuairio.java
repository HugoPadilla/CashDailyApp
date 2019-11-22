package com.wenitech.cashdaily.Model;

public class Usuairio {

    private String aNombreUsuario;
    private String bInicialNombre;
    private int cTotalEfectivo, dCobradoHoy, eCobradoMes;


    public Usuairio() {
    }

    public Usuairio(String aNombreUsuario, String bInicialNombre, int cTotalEfectivo, int dCobradoHoy, int eCobradoMes) {
        this.aNombreUsuario = aNombreUsuario;
        this.bInicialNombre = bInicialNombre;
        this.cTotalEfectivo = cTotalEfectivo;
        this.dCobradoHoy = dCobradoHoy;
        this.eCobradoMes = eCobradoMes;
    }

    public String getaNombreUsuario() {
        return aNombreUsuario;
    }

    public void setaNombreUsuario(String aNombreUsuario) {
        this.aNombreUsuario = aNombreUsuario;
    }

    public String getbInicialNombre() {
        return bInicialNombre;
    }

    public void setbInicialNombre(String bInicialNombre) {
        this.bInicialNombre = bInicialNombre;
    }

    public int getcTotalEfectivo() {
        return cTotalEfectivo;
    }

    public void setcTotalEfectivo(int cTotalEfectivo) {
        this.cTotalEfectivo = cTotalEfectivo;
    }

    public int getdCobradoHoy() {
        return dCobradoHoy;
    }

    public void setdCobradoHoy(int dCobradoHoy) {
        this.dCobradoHoy = dCobradoHoy;
    }

    public int geteCobradoMes() {
        return eCobradoMes;
    }

    public void seteCobradoMes(int eCobradoMes) {
        this.eCobradoMes = eCobradoMes;
    }
}
