package com.wenitech.cashdaily.NewClient;

import com.google.firebase.Timestamp;

public interface Interface {

    interface view{
        void hidenform();
        void showform();
        void showPrgres();
        void hidenProgres();
        void inicarRegistro();
        boolean isFormularioValido();
        void onSuces();
        void onError();
    }

    interface presenter{
        void onDestroid();
        void presentarRegistro(String identificacion, String nombre, String inicialNombre, String telefono, String ubicacion,int valorPrestamo, int deudaPrestamo, boolean estado);
    }

    interface model{
        void registrar(String identificacion, String nombre, String inicialNombre, String telefono, String ubicacion,int valorPrestamo, int deudaPrestamo, boolean estado);
    }

    interface taskListener{
        void succes();
        void error();
    }
}
