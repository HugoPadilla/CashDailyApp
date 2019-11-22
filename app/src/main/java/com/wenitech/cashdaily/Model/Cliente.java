package com.wenitech.cashdaily.Model;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class Cliente {

    private Timestamp aFechaCreacion;//
    private DocumentReference bDocReferencia;
    private String cIdentificacion;//
    private String dNombreCliente;//
    private String eInicialNombre;
    private String fTelefono;
    private String gUbicacion;//
    private int hValorPrestamo;
    private int iDeudaPrestamo;
    private Boolean jAtrasado;


    public Cliente() {
    }

    public Cliente(Timestamp aFechaCreacion, DocumentReference bDocReferencia, String cIdentificacion,
                   String dNombreCliente, String eInicialNombre, String fTelefono, String gUbicacion,
                   int hValorPrestamo, int iDeudaPrestamo, Boolean jAtrasado) {
        this.aFechaCreacion = aFechaCreacion;
        this.bDocReferencia = bDocReferencia;
        this.cIdentificacion = cIdentificacion;
        this.dNombreCliente = dNombreCliente;
        this.eInicialNombre = eInicialNombre;
        this.fTelefono = fTelefono;
        this.gUbicacion = gUbicacion;
        this.hValorPrestamo = hValorPrestamo;
        this.iDeudaPrestamo = iDeudaPrestamo;
        this.jAtrasado = jAtrasado;
    }

    public Timestamp getaFechaCreacion() {
        return aFechaCreacion;
    }

    public void setaFechaCreacion(Timestamp aFechaCreacion) {
        this.aFechaCreacion = aFechaCreacion;
    }

    public DocumentReference getbDocReferencia() {
        return bDocReferencia;
    }

    public void setbDocReferencia(DocumentReference bDocReferencia) {
        this.bDocReferencia = bDocReferencia;
    }

    public String getcIdentificacion() {
        return cIdentificacion;
    }

    public void setcIdentificacion(String cIdentificacion) {
        this.cIdentificacion = cIdentificacion;
    }

    public String getdNombreCliente() {
        return dNombreCliente;
    }

    public void setdNombreCliente(String dNombreCliente) {
        this.dNombreCliente = dNombreCliente;
    }

    public String geteInicialNombre() {
        return eInicialNombre;
    }

    public void seteInicialNombre(String eInicialNombre) {
        this.eInicialNombre = eInicialNombre;
    }

    public String getfTelefono() {
        return fTelefono;
    }

    public void setfTelefono(String fTelefono) {
        this.fTelefono = fTelefono;
    }

    public String getgUbicacion() {
        return gUbicacion;
    }

    public void setgUbicacion(String gUbicacion) {
        this.gUbicacion = gUbicacion;
    }

    public int gethValorPrestamo() {
        return hValorPrestamo;
    }

    public void sethValorPrestamo(int hValorPrestamo) {
        this.hValorPrestamo = hValorPrestamo;
    }

    public int getiDeudaPrestamo() {
        return iDeudaPrestamo;
    }

    public void setiDeudaPrestamo(int iDeudaPrestamo) {
        this.iDeudaPrestamo = iDeudaPrestamo;
    }

    public Boolean getjAtrasado() {
        return jAtrasado;
    }

    public void setjAtrasado(Boolean jAtrasado) {
        this.jAtrasado = jAtrasado;
    }
}
