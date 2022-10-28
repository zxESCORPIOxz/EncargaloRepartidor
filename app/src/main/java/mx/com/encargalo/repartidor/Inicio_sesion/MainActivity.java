package mx.com.encargalo.repartidor.Inicio_sesion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    TextView me_menutxtNombreUsuario;
    ImageView me_menuimgvwimgUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
    //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setActionView(R.layout.opcionarrow);
        navigationView.getMenu().getItem(1).setActionView(R.layout.opcionarrow);
        navigationView.getMenu().getItem(2).setActionView(R.layout.opcionarrow);
        navigationView.getMenu().getItem(3).setActionView(R.layout.opcionarrow);
        navigationView.getMenu().getItem(4).setActionView(R.layout.opcionarrow);

        View headView = navigationView.getHeaderView(0);


        SharedPreferences sharedPreferences =
                getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);

        me_menutxtNombreUsuario = headView.findViewById(R.id.me_menutxtNombreUsuario);
        me_menuimgvwimgUsuario = headView.findViewById(R.id.me_menuimgvwimgUsuario);

        me_menutxtNombreUsuario.setText(sharedPreferences
                .getString(DATOS.VARGOB_ID_USUARIO,"Nombre no encontrado"));

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menuinicio,
                R.id.nav_miperfil,
                R.id.nav_pedidos,
                R.id.nav_notificacion,
                R.id.nav_soporte)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}