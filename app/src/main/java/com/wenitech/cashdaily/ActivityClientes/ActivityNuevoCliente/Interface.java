package com.wenitech.cashdaily.ActivityClientes.ActivityNuevoCliente;

public interface Interface {

    interface view{
        void hidenForm();
        void showForm();
        void showProgres();
        void hidenProgres();
        void iniciarRegistro();
        boolean isFormularioValid();
        void onSucces();
        void onError();
    }

    interface presenter{
        void onDestroid();
        void presentarRegistro(String nombreCliente, String identificacion, String genero, String telefono, String ciudad,String direccion);
    }

    interface model{
        void registrar(String nombreCliente, String identificacion, String genero, String telefono, String ciudad,String direccion);
    }

    interface taskListener{
        void succes();
        void error();
    }
}
