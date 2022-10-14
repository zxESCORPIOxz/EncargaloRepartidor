package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class sp_frgpreguntas extends Fragment {
    Button sp_pfbtnprg1, sp_pfbtnprg2, sp_pfbtnprg3, sp_pfbtnprg4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sp_frgpreguntas, container, false);

        sp_pfbtnprg1 = v.findViewById(R.id.sp_pfbtnprg1);
        sp_pfbtnprg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_preguntas_to_nav_detallepregunta);
            }
        });

        return v;
    }
}