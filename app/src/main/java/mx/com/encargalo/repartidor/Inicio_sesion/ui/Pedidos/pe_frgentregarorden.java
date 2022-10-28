package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mx.com.repartidor.R;

public class pe_frgentregarorden extends Fragment {
    Button pe_etobtncobrar;
    Dialog dialogyes_no;
    View viewinterno;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-12.051392, -75.198301);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            CameraUpdate myUbicacion = CameraUpdateFactory.newLatLngZoom(sydney, 16);
            googleMap.animateCamera(myUbicacion);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgentregarorden, container, false);
        dialogyes_no = new Dialog(getContext());
        pe_etobtncobrar = view.findViewById(R.id.pe_etobtncobrar);
        pe_etobtncobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewinterno = v;
                dialogyes_no.setContentView(R.layout.pe_dialogconfirmarcobro);
                dialogyes_no.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogyes_no.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogyes_no.setCancelable(false);
                Button pe_dynbtnno = dialogyes_no.findViewById(R.id.pe_dynbtnno);
                Button pe_dynbtnsi = dialogyes_no.findViewById(R.id.pe_dynbtnsi);
                pe_dynbtnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogyes_no.dismiss();
                        Navigation.findNavController(viewinterno).navigate(R.id.action_nav_entregarpedido_to_nav_cobrarpedido);
                    }
                });
                pe_dynbtnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogyes_no.dismiss();
                    }
                });
                dialogyes_no.show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}