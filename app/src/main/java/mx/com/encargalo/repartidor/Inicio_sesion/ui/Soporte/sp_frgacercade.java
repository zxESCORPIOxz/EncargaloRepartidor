package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import mx.com.repartidor.R;


public class sp_frgacercade extends Fragment{
    Button btnWebsite, btnFacebook, btnInstagram, btnDesarrolladores;
    String url1, url2, url3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sp_frgacercade, container, false);

        btnWebsite=view.findViewById(R.id.btn_so_05_website);
        btnFacebook=view.findViewById(R.id.btn_so_05_facebook);
        btnInstagram=view.findViewById(R.id.btn_so_05_instagram);
        btnDesarrolladores=view.findViewById(R.id.btn_so_05_desarrolladores);
        url1="https://es-la.facebook.com/";

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Uri uri = Uri.parse(url1);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url1);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url1);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnDesarrolladores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_desarrolladores);
            }
        });
        return view;
    }

}