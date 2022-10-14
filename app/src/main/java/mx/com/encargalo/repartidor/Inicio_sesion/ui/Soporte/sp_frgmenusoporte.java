package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;

public class sp_frgmenusoporte extends Fragment {
    Button sp_mspbtncentrodeayuda, sp_mspbtnteminosycondiciones, sp_mspbtncompartirencargalo,
            sp_mspbtnacercade, sp_mspbtncerrarsesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sp_frgmenusoporte, container, false);

        sp_mspbtncentrodeayuda = v.findViewById(R.id.sp_mspbtncentrodeayuda);
        sp_mspbtnteminosycondiciones = v.findViewById(R.id.sp_mspbtnteminosycondiciones);
        sp_mspbtncompartirencargalo = v.findViewById(R.id.sp_mspbtncompartirencargalo);
        sp_mspbtnacercade = v.findViewById(R.id.sp_mspbtnacercade);
        sp_mspbtncerrarsesion = v.findViewById(R.id.sp_mspbtncerrarsesion);

        sp_mspbtncentrodeayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_soporte_to_nav_centrodeayuda);
            }
        });
        return v;
    }
}