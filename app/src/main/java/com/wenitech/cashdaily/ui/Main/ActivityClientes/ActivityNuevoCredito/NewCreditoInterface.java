package com.wenitech.cashdaily.ui.Main.ActivityClientes.ActivityNuevoCredito;

import com.wenitech.cashdaily.Data.model.Credito;

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
