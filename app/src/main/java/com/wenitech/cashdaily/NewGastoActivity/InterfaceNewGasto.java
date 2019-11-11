package com.wenitech.cashdaily.NewGastoActivity;

public interface InterfaceNewGasto {
    interface view{
        void GuardatDatos();
        boolean IsFormValid();

        void ShowPrograsBar();
        void HidenProgresBar();

        void OnSucess();
        void OnError();
    }

    interface presenter{
        void GuardarDatos(String valor, String fecha, String hora, String descripcion);
    }

    interface model{
        void GuardarDatos(String valor, String fecha, String hora, String descripcion);
    }

    interface taskListener{
        void Sucess();
        void Error();
    }
}
