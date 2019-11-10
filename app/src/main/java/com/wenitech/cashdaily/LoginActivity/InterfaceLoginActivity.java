package com.wenitech.cashdaily.LoginActivity;

public interface InterfaceLoginActivity {
    interface view{
        void BotonIngresarPress();
        void BotonCrearCuentaPress();

        boolean FormValido();

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
