package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frgcobrarorden extends Fragment {
    TextView pe_cobtxtidorden, pe_cobtxttotal, pe_cobtxtmediopago, pe_cobtxtnombretienda,
            pe_cobtxtgantienda, pe_cobtxtganrepartidor;
    Button pe_cobbtnregistrarcobro, pe_cobbtnentregarorden;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    View viewinterno;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgcobrarorden, container, false);

        request = Volley.newRequestQueue(getContext());

        pe_cobtxtidorden = view.findViewById(R.id.pe_cobtxtidorden);
        pe_cobtxttotal = view.findViewById(R.id.pe_cobtxttotal);
        pe_cobtxtmediopago = view.findViewById(R.id.pe_cobtxtmediopago);
        pe_cobtxtnombretienda = view.findViewById(R.id.pe_cobtxtnombretienda);
        pe_cobtxtgantienda = view.findViewById(R.id.pe_cobtxtgantienda);
        pe_cobtxtganrepartidor = view.findViewById(R.id.pe_cobtxtganrepartidor);

        sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        cargar_detalle(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));

        pe_cobbtnregistrarcobro = view.findViewById(R.id.pe_cobbtnregistrarcobro);
        pe_cobbtnregistrarcobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pe_cobbtnentregarorden.setEnabled(true);
                pe_cobbtnentregarorden.setVisibility(View.VISIBLE);
                pe_cobbtnregistrarcobro.setEnabled(false);
                pe_cobbtnregistrarcobro.setVisibility(View.INVISIBLE);
            }
        });
        pe_cobbtnentregarorden = view.findViewById(R.id.pe_cobbtnentregarorden);
        pe_cobbtnentregarorden.setEnabled(false);
        pe_cobbtnentregarorden.setVisibility(View.INVISIBLE);
        pe_cobbtnentregarorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewinterno = v;
                me_modupdestadoorden(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));
            }
        });

        return view;
    }

    public void cargar_detalle(final String idOrden){
        String APIREST_URL = DATOS.IP_SERVER+ "c_detalle_cobro_orden.php?"+
                "idOrden=" + idOrden;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        float total = Float.parseFloat(response.optString("GananciaTienda"))
                                + Float.parseFloat(response.optString("GananciaRepartidor"));
                        DecimalFormat formato = new DecimalFormat("#.00");
                        pe_cobtxtidorden.setText("Cargar pago de la orden : " + idOrden);
                        pe_cobtxttotal.setText("Monto a cobrar: : " + formato.format(total));
                        pe_cobtxtmediopago.setText("Medio de pago : Efectivo");
                        pe_cobtxtnombretienda.setText("Megocio de venta : " + response.optString("NombreTienda"));
                        pe_cobtxtgantienda.setText("Retorna a tienda : " + response.optString("GananciaTienda"));
                        pe_cobtxtganrepartidor.setText("Ganancia de repartidor : " + response.optString("GananciaRepartidor"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), DATOS.NO_ENCONTRADO, Toast.LENGTH_SHORT).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
    public void me_modupdestadoorden(String idOrden){
        String APIREST_URL = DATOS.IP_SERVER+ "m_estado_orden_encamino_entregado.php?"+
                "idOrden=" + idOrden;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Navigation.findNavController(viewinterno).navigate(R.id.action_nav_cobrarpedido_to_nav_ordencompletada);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
                });
        request.add(jsonObjectRequest);
    }
}