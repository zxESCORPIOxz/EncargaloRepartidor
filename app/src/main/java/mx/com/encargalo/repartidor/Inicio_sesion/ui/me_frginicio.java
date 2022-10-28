package mx.com.encargalo.repartidor.Inicio_sesion.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.repartidor.R;


public class me_frginicio extends Fragment {
    Button me_varstringcomensar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me_frginicio, container, false);

        me_varstringcomensar = view.findViewById(R.id.me_mebtncontimuar);

        me_varstringcomensar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_menuinicio_to_nav_miperfil);
            }
        });

        return view;
    }
}