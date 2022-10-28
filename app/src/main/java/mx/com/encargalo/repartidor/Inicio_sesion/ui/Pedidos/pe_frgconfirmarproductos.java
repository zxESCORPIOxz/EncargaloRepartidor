package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;


public class pe_frgconfirmarproductos extends Fragment {
    Button pe_rgobtnrecoger;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pe_frgconfirmarproductos, container, false);

        pe_rgobtnrecoger = view.findViewById(R.id.pe_rgobtnrecoger);
        pe_rgobtnrecoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_confirmarproductos_to_nav_entregarpedido);
            }
        });

        return view;
    }
}