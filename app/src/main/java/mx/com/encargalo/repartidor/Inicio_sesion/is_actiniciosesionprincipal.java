package mx.com.encargalo.repartidor.Inicio_sesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.repartidor.R;

public class is_actiniciosesionprincipal extends AppCompatActivity {
    Button is_ispbtnfacebook,is_ispbtngmail,is_ispbtncrearcuenta;
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
                Intent i = new Intent( is_actiniciosesionprincipal.this, MainActivity.class);
                startActivity(i);
            }
        });
        is_ispbtngmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( is_actiniciosesionprincipal.this, MainActivity.class);
                startActivity(i);
            }
        });
        is_ispbtncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( is_actiniciosesionprincipal.this, is_actregistrarusuario.class);
                startActivity(i);
            }
        });
    }
}