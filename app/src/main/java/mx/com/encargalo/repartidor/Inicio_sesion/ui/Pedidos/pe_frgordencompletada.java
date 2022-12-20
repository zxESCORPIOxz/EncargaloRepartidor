//Creado por Duran Llamacuri Kevin - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frgordencompletada extends Fragment {
    ImageButton pe_ocnebtnclose;
    TextView pe_ocptxtidorden;
    Button pe_ocbtnRegistrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgordencompletada, container, false);

        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);

        pe_ocnebtnclose = view.findViewById(R.id.pe_ocnebtnclose);
        pe_ocptxtidorden = view.findViewById(R.id.pe_ocptxtidorden);
        pe_ocptxtidorden.setText("Orden N° "+sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,"")+" fue entregada exitosamente");
        pe_ocnebtnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_ordencompletada_to_nav_menuinicio);
            }
        });
        pe_ocbtnRegistrar = view.findViewById(R.id.pe_ocbtnRegistrar);
        pe_ocbtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_ordencompletada_to_nav_menuinicio);
            }
        });

        return view;
    }
}