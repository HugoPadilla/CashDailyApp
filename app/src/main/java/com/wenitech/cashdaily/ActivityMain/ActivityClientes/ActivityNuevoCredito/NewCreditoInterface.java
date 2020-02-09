package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito;

import com.wenitech.cashdaily.Model.Credito;

public interface NewCreditoInterface {
    interface view{
        void registrarCredito();
        void ocultarView();
        void mostrarView();
        boolean isFormularioCreditoValido();
        boolean isFormularioModalidadValido();
        void onSucces();
        void onError();
    }
    interface presente{
        void registrarCredito(Credito credito, String referenciaCliente);
    }
    interface model{
        void registrarCredito(Credito credito, String referenciaCliente);
    }
    interface taskListener{
        void onSucces();
        void onError();
    }
}
