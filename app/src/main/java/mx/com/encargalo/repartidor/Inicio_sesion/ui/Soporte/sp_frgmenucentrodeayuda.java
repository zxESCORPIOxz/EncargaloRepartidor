package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class sp_frgmenucentrodeayuda extends Fragment {
    Button sp_mcabtnpreguntas, sp_mcabtnvideos, sp_mcabtnconsulta;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sp_frgmenucentrodeayuda, container, false);

        sp_mcabtnpreguntas = v.findViewById(R.id.sp_mcabtnpreguntas);
        sp_mcabtnvideos = v.findViewById(R.id.sp_mcabtnvideos);
        sp_mcabtnconsulta = v.findViewById(R.id.sp_mcabtnconsulta);

        sp_mcabtnpreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_centrodeayuda_to_nav_preguntas);
            }
        });

        return v;
    }
}