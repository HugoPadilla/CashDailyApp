package com.wenitech.cashdaily.ui.Main.ActivityClientes.ActivityCreditoCliente;

import com.wenitech.cashdaily.Data.model.Cuota;

public interface CreditoClienteInterface {

    interface view{
        void instanciarFirebase();
        void iniciarToolbar();
        void iniciarAppBar();
        void castingView();
        void iniciarRecyclerView();
        void listenerView();
        void snapshotCredito();
        void dialogoNuevaCuota();
        void abriDialogoProgreso();
        void cerraDialogoProgreso();
        void dialogoOnSucces(String mensaje);
        void dialogoOnError(String mensaje);
    }
    interface presenter{
        void agregarNuevaCuota(Cuota cuota, String referenciaCredito, String referenciasCliente);
    }
    interface model{
        void agregarNuevaCuota(Cuota cuota, String referenciaCredito, String refereciaCliente);
    }
    interface taskListener{
        void onSucces(String mensaje);
        void onError(String mensaje);
    }
}
