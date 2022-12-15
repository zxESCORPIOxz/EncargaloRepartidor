//Creado por Briceño Malpartida Douglas Igancio - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import mx.com.repartidor.R;


public class sp_frgMenuterminoscondiciones extends Fragment implements View.OnClickListener {

    Button btnTermsConsUso, btnPolitPrivacidad, btnProducProhibidos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sp_frgmenuterminoscondiciones, container, false);

        btnTermsConsUso = view.findViewById(R.id.btn_so_04_terminos_condiciones);
        btnPolitPrivacidad = view.findViewById(R.id.btn_so_04_politica_privacidad);
        btnProducProhibidos = view.findViewById(R.id.btn_so_04_produc_prohibidos);

        btnTermsConsUso.setOnClickListener(this);
        btnPolitPrivacidad.setOnClickListener(this);
        btnProducProhibidos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.btn_so_04_terminos_condiciones:{
                bundle.putString("tipo", "terminos_condiciones");
                sp_frgTerminoscondicionesdetalles dialogoDetalle = new sp_frgTerminoscondicionesdetalles();
                dialogoDetalle.setArguments(bundle);
                dialogoDetalle.show(getActivity().getSupportFragmentManager(), "terminos_condiciones");
                break;
            }
            case R.id.btn_so_04_politica_privacidad:{
                bundle.putString("tipo", "politica_privacidad");
                sp_frgTerminoscondicionesdetalles dialogoDetalle = new sp_frgTerminoscondicionesdetalles();
                dialogoDetalle.show(getActivity().getSupportFragmentManager(), "politica_privacidad");
                dialogoDetalle.setArguments(bundle);
                break;
            }
            case R.id.btn_so_04_produc_prohibidos:{
                bundle.putString("tipo", "productos_prohibidos");
                sp_frgTerminoscondicionesdetalles dialogoDetalle = new sp_frgTerminoscondicionesdetalles();
                dialogoDetalle.show(getActivity().getSupportFragmentManager(), "productos_prohibidos");
                dialogoDetalle.setArguments(bundle);
                break;
            }
        }
    }
}