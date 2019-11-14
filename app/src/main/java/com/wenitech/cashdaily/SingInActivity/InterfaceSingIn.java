package com.wenitech.cashdaily.SingInActivity;

public interface InterfaceSingIn {

    interface view{

        void crearCuenta();
        boolean isFormValid();

        void ShowFormulario();
        void HidenFormulario();

        void onSucess(String emailSucess);
        void onExist();
        void onError();
    }

    interface presenter{
        void CrearCuenta(String userName, String email, String password);
    }

    interface model {
        void CrearCuenta(String userName, String email, String password);
    }

    interface taskListener{
        void onSucess(String emailSucess);
        void onExist();
        void onError();
    }
}
