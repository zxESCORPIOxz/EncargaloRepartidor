package mx.com.encargalo.repartidor.Inicio_sesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

public class is_actiniciosesionprincipal extends AppCompatActivity {
    Button is_ispbtnfacebook, is_ispbtngmail, is_ispbtncrearcuenta;
    String varstringcorreo, varstringdocumento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_actiniciosesionprincipal);

        is_ispbtnfacebook = findViewById(R.id.is_ispbtnfacebook);
        is_ispbtngmail = findViewById(R.id.is_ispbtngmail);
        is_ispbtncrearcuenta = findViewById(R.id.is_ispbtncrearcuenta);

        is_ispbtnfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varstringcorreo = is_fctgetCorreoByFacebook();
                is_fctgetDocumentoUsuario();
                if(varstringdocumento.length()!=0){
                    is_modir_a_Menu();
                }else {
                    is_modir_a_RegistroUsuario();
                }
            }
        });

        is_ispbtngmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varstringcorreo = is_fctgetCorreoByGoogle();
                is_fctgetDocumentoUsuario();
                if(varstringdocumento.length()!=0){
                    is_modir_a_Menu();
                }else {
                    is_modir_a_RegistroUsuario();
                }
            }
        });

        is_ispbtncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_modir_a_RegistroUsuario();
            }
        });
    }

    public void is_modir_a_RegistroUsuario(){
        Intent i = new Intent( is_actiniciosesionprincipal.this,
                is_actregistrarusuario.class);
        startActivity(i);
    }

    public void is_modir_a_Menu(){
        Intent i = new Intent( is_actiniciosesionprincipal.this,
                MainActivity.class);
        SharedPreferences sharedPreferences =
                getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATOS.VARGOB_ID_USUARIO, varstringdocumento);
        editor.apply();
        startActivity(i);
    }

    public String is_fctgetCorreoByGoogle(){
        return "correo03@gmail.com";
    }

    public String is_fctgetCorreoByFacebook(){
        return "correo03@gmail.com";
    }

    public void is_fctgetDocumentoUsuario(){
        varstringdocumento = "96489898";
    }

}