package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.repartidor.Adapters.pe_adplistaproductos;
import mx.com.encargalo.repartidor.Entidades.pe_claseproducto_lista;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frgdetallehistorial extends Fragment {
    TextView pe_dettxtidorden, pe_dettxtfechahora, pe_dettxtclientenombre, pe_dettxtnombredireccion,
            pe_dettxtmonto, pe_dettxttipodepago, pe_dettxthorayfechadeentrega, pe_dettxttiempo;

    RecyclerView pe_detrclvlistaproductos;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<pe_claseproducto_lista> lista_productos;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pe_frgdetallehistorial, container, false);

        request = Volley.newRequestQueue(getContext());

        pe_dettxtidorden = view.findViewById(R.id.pe_dettxtidorden);
        pe_dettxtfechahora = view.findViewById(R.id.pe_dettxtfechahora);
        pe_dettxtclientenombre = view.findViewById(R.id.pe_dettxtclientenombre);
        pe_dettxtnombredireccion = view.findViewById(R.id.pe_dettxtnombredireccion);
        pe_dettxtmonto = view.findViewById(R.id.pe_dettxtmonto);
        pe_dettxttipodepago = view.findViewById(R.id.pe_dettxttipodepago);
        pe_dettxthorayfechadeentrega = view.findViewById(R.id.pe_dettxthorayfechadeentrega);
        pe_dettxttiempo = view.findViewById(R.id.pe_dettxttiempo);

        pe_detrclvlistaproductos = view.findViewById(R.id.pe_detrclvlistaproductos);

        sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);

        me_modgetproductos(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));
      return view;
    }


    public void me_modgetproductos(String idOrden){
        String APIREST_URL = DATOS.IP_SERVER+ "c_datos_orden_productos.php?"+
                "idOrden=" + idOrden;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        pe_dettxtidorden.setText("ID ORDEN : "+response.optString("idOrden"));
                        pe_dettxtfechahora.setText(response.optString("HoraFechaInicial"));
                        pe_dettxtclientenombre.setText("Cliente   : "+response.optString("Nombre"));
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> list = null;
                        try {
                            list = geocoder.getFromLocation(
                                    Double.parseDouble(response.optString("Latitud")),
                                    Double.parseDouble(response.optString("Longitud")),
                                    1);
                            if (!list.isEmpty()) {
                                Address DirCalle = list.get(0);
                                pe_dettxtnombredireccion.setText("Dirección : "+DirCalle.getAddressLine(0));
                            }
                        } catch (IOException e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        pe_dettxtmonto.setText("Monto a cobrar : "+response.optInt("mCobrar"));
                        pe_dettxttipodepago.setText("Tipo de pago : "+response.optString("TipoPago"));
                        pe_dettxthorayfechadeentrega.setText("Hora y fecha pactada : "+response.optString("HoraFechaEntrega"));
                        pe_dettxttiempo.setText(response.optString("TiempoEntrega")+" min");
                        JSONArray myjson = null;
                        try {
                            myjson = response.getJSONArray("Producto");
                            pe_claseproducto_lista producto = null;
                            lista_productos = new ArrayList<>();
                            for (int i = 0; i < myjson.length(); i++) {
                                JSONObject myjsonObject = myjson.getJSONObject(i);
                                producto = new pe_claseproducto_lista(
                                        myjsonObject.getString("Descripcion"),
                                        myjsonObject.getString("Cantidad"),
                                        myjsonObject.getString("SubTotal")
                                );
                                lista_productos.add(producto);
                            }
                            pe_detrclvlistaproductos.setLayoutManager(new LinearLayoutManager(getContext()));
                            pe_adplistaproductos adapter = new pe_adplistaproductos(getContext(),lista_productos);
                            pe_detrclvlistaproductos.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), DATOS.NO_ENCONTRADO, Toast.LENGTH_SHORT).show();
                        }
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

}