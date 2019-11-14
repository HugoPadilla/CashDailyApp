package com.wenitech.cashdaily.MainActivity.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.wenitech.cashdaily.Adapter.RecyclerViewHomeAdapter;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.Model.Usuairio;
import com.wenitech.cashdaily.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.collection("usuarios").document(auth.getUid());

    private TextView tv_efectivo, tv_hoy, tv_este_mes;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        tv_efectivo = view.findViewById(R.id.tv_total_efectivo);
        tv_hoy = view.findViewById(R.id.tv_hoy);
        tv_este_mes = view.findViewById(R.id.tv_este_mes);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {

                    Usuairio usuairio = documentSnapshot.toObject(Usuairio.class);
                    if (usuairio != null) {

                        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
                        simbolo.setDecimalSeparator(',');
                        simbolo.setGroupingSeparator('.');
                        DecimalFormat decimalFormat = new DecimalFormat("###,###.##",simbolo);

                        tv_efectivo.setText(decimalFormat.format(usuairio.getTotalEfectivo()));
                        tv_hoy.setText(decimalFormat.format(usuairio.getCobradoHoy()));
                        tv_este_mes.setText(decimalFormat.format(usuairio.getEsteMes()));
                    }

                } else {

                }

            }
        });

    }

    public void clikfecha (View v){
        Toast.makeText(getActivity(), "Agregar fecha", Toast.LENGTH_SHORT).show();
    }
    
}
