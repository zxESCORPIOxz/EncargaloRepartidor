package mx.com.encargalo.repartidor.Inicio_sesion.ui.Mi_perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;


public class pf_frgperfil extends Fragment {
    Button  pf_pfbtnmodregistrodevehiculo,
            pf_pfbtnmodregistrodelicencia,
            pf_pfbtnmodregistrodeantecedentes,
            pf_pfbtnmodtargetadepropiedad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pf_frgperfil, container, false);

        pf_pfbtnmodregistrodevehiculo = view.findViewById(R.id.pf_pfbtnmodregistrodevehiculo);
        pf_pfbtnmodregistrodevehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodelicencia = view.findViewById(R.id.pf_pfbtnmodregistrodelicencia);
        pf_pfbtnmodregistrodelicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodeantecedentes = view.findViewById(R.id.pf_pfbtnmodregistrodeantecedentes);
        pf_pfbtnmodregistrodeantecedentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodtargetadepropiedad = view.findViewById(R.id.pf_pfbtnmodtargetadepropiedad);
        pf_pfbtnmodtargetadepropiedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });

        return view;
    }
}