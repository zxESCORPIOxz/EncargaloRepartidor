//Creado por Duran Llamacuri Kevin - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import mx.com.encargalo.repartidor.Inicio_sesion.MainActivity;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frgmenupedidos extends Fragment {
    Button pe_mpebtnordenencurso, pe_mpebtnhistorialdeordenes, pe_mpebtncobrosrealizados;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    View viewaux;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pe_frgmenupedidos, container, false);

        request= Volley.newRequestQueue(getContext());

        pe_mpebtnordenencurso = view.findViewById(R.id.pe_mpebtnordenencurso);
        pe_mpebtnhistorialdeordenes = view.findViewById(R.id.pe_mpebtnhistorialdeordenes);
//        pe_mpebtncobrosrealizados = view.findViewById(R.id.pe_mpebtncobrosrealizados);

        pe_mpebtnordenencurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewaux=v;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 123);
                }else {
                    SharedPreferences sharedPreferences =
                            getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                    String APIREST_URL = DATOS.IP_SERVER+ "c_orden_actual_por_idrepartidor.php?"+
                            "idRepartidor=" + sharedPreferences.getString(DATOS.VARGOB_ID_REPARTIDOR,"");
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                            new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            SharedPreferences sharedPreferences =
                                    getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(DATOS.VARGOB_ID_ORDEN, response.optString("idOrden"));
                            editor.apply();
                            switch (response.optString("odEstado")){
                                case "PREPARADO":{
                                    Navigation.findNavController(viewaux).navigate(R.id.action_nav_pedidos_to_nav_recogerorden);
                                }break;
                                case "EN CAMINO":{
                                    Navigation.findNavController(viewaux).navigate(R.id.action_nav_pedidos_to_nav_entregarpedido);
                                }break;
                            }

                        }
                    },
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(viewaux, R.string.pe_varstringnoexistependientes, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                    request.add(jsonObjectRequest);

                }
            }
        });
        pe_mpebtnhistorialdeordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_pedidos_to_nav_historialordenes);
            }
        });
/*        pe_mpebtncobrosrealizados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_pedidos_to_nav_historialcobros);
            }
        });*/

        return view;
    }
}