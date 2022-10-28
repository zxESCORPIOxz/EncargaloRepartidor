package mx.com.encargalo.repartidor.Inicio_sesion.ui.Mi_perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class pf_frgcargardocumentos extends Fragment {
    Button pf_rubtnRegistrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pf_frgcargardocumentos, container, false);

        pf_rubtnRegistrar = view.findViewById(R.id.pf_rubtnRegistrar);
        pf_rubtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_cargardocumento_to_nav_docsubido);
            }
        });
        return view;
    }
}