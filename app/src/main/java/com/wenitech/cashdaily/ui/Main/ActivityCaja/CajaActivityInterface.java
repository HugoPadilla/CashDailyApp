package com.wenitech.cashdaily.ui.Main.ActivityCaja;

public interface CajaActivityInterface {

    interface view {
        void instanciarFirebase();
        void iniciarToolbar();
        void castingViewAndListener();
        void iniciarRecyclerViewNovimientos();
        void snapshotListenerCaja();
        void dialogoAgregarDinero();
        void dialogoRetirarDinero();
        void abrirDialogoProgreso(String mensaje);
        void cerraDialogoProgreso();
        void mensajeOnSucces(String mensaje);
        void mensajeOnError(String mensaje);
    }
    interface presenter{
        void OnDestroid();
        void agregarDineroCaja(double valorAgregar);
        void retirarDineroCaja(double valorRetirar);
    }
    interface model{
        void agregarDineroCaja(double valorAgregar);
        void retirarDineroCaja(double valorRetirar);
    }
    interface taskListener{
        void OnSucess(String mensaje);
        void OnError(String mensaje);
    }
}

