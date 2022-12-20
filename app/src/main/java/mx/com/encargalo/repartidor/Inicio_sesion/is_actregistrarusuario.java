//Creado por Duran Llamacuri Kevin - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Inicio_sesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import mx.com.repartidor.R;

public class is_actregistrarusuario extends AppCompatActivity {
    Button is_rubtnRegistrar;
    TextInputEditText is_ruedtTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_actregistrarusuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.is_iconback);
        getSupportActionBar().setTitle(R.string.toolbar_is);
        is_rubtnRegistrar=findViewById(R.id.is_rubtnRegistrar);
        is_ruedtTelefono=findViewById(R.id.is_ruedtTelefono);
        is_ruedtTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        is_rubtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( is_actregistrarusuario.this, is_actvalidacioncodigo.class);
                startActivity(i);
            }
        });
    }
}