package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class pe_frgmenupedidos extends Fragment {
    Button pe_mpebtnordenencurso, pe_mpebtnhistorialdeordenes, pe_mpebtncobrosrealizados;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pe_frgmenupedidos, container, false);

        pe_mpebtnordenencurso = view.findViewById(R.id.pe_mpebtnordenencurso);
        pe_mpebtnhistorialdeordenes = view.findViewById(R.id.pe_mpebtnhistorialdeordenes);
        pe_mpebtncobrosrealizados = view.findViewById(R.id.pe_mpebtncobrosrealizados);

        pe_mpebtnordenencurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_pedidos_to_nav_recogerorden);
            }
        });
        pe_mpebtnhistorialdeordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_pedidos_to_nav_historialordenes);
            }
        });
        pe_mpebtncobrosrealizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_pedidos_to_nav_historialcobros);
            }
        });

        return view;
    }
}