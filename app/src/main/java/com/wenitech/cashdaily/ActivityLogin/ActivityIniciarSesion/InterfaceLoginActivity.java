package com.wenitech.cashdaily.ActivityLogin.ActivityIniciarSesion;

public interface InterfaceLoginActivity {
    interface view{
        void iniciarSesion();
        void goActivityRegistrarse();

        boolean isFormularioValido();

        void ShowFormLogin();
        void HidenFormLogin();

        void updatesUi();
        void OnErro();
    }

    interface presenter{
        void IniciarSesion(String email, String password);
    }

    interface model{
        void InciarSesion(String emai, String password);
    }

    interface taskListener{
        void LoginSuces();
        void LoginError();

    }
}
