package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class pe_frgcobrarorden extends Fragment {
    Button pe_cobbtnregistrarcobro, pe_cobbtnentregarorden;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgcobrarorden, container, false);

        pe_cobbtnregistrarcobro = view.findViewById(R.id.pe_cobbtnregistrarcobro);
        pe_cobbtnregistrarcobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pe_cobbtnentregarorden.setEnabled(true);
                pe_cobbtnregistrarcobro.setEnabled(false);
            }
        });
        pe_cobbtnentregarorden = view.findViewById(R.id.pe_cobbtnentregarorden);
        pe_cobbtnentregarorden.setEnabled(false);
        pe_cobbtnentregarorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_cobrarpedido_to_nav_ordencompletada);
            }
        });

        return view;
    }
}