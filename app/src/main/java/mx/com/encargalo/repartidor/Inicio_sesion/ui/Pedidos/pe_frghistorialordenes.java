package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.repartidor.Adapters.pe_adhtolistaorden;
import mx.com.encargalo.repartidor.Entidades.pe_claseorden_lista_hto;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frghistorialordenes extends Fragment {

    RecyclerView pe_htorclvlistaordenes;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SharedPreferences preferences;
    ArrayList<pe_claseorden_lista_hto> listaordenes;
    SearchView pe_htosrchlistaordenes;
    Spinner pe_htospnrlistaordenes;
    pe_adhtolistaorden adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frghistorialordenes, container, false);

        preferences = getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, Context.MODE_PRIVATE);

        listaordenes = new ArrayList<>();

        request= Volley.newRequestQueue(getContext());
        pe_htospnrlistaordenes = view.findViewById(R.id.pe_htospnrlistaordenes);
        pe_htospnrlistaordenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(adapter != null){
                    adapter.filtrado_by_estado(pe_htospnrlistaordenes.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    pe_htosrchlistaordenes = view.findViewById(R.id.pe_htosrchlistaordenes);
    pe_htosrchlistaordenes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.filtrado_by_id(newText);
            return false;
        }
    });
        pe_htorclvlistaordenes = view.findViewById(R.id.pe_htorclvlistaordenes);
        me_modgetordenes();
        return view;
    }

    public void me_modgetordenes(){
        String APIREST_URL = DATOS.IP_SERVER+ "c_lista_ordenes_por_idrepartidor.php?"+
                "idrepartidor=" + preferences.getString(DATOS.VARGOB_ID_REPARTIDOR,"");
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray myjson = response.getJSONArray("Consulta");
                            pe_claseorden_lista_hto orden = null;
                            for (int i = 0; i < myjson.length(); i++) {
                                JSONObject myjsonObject = myjson.getJSONObject(i);
                                orden = new pe_claseorden_lista_hto(
                                        myjsonObject.getString("idOrden"),
                                        myjsonObject.getString("odFechaPedido"),
                                        myjsonObject.getString("odHoraPedido"),
                                        myjsonObject.getString("odEstado"),
                                        myjsonObject.getString("perNombres"),
                                        myjsonObject.getString("perApellidos")
                                );
                                listaordenes.add(orden);
                            }
                            showRecicler();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
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

    private void showRecicler() {
        pe_htorclvlistaordenes.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new pe_adhtolistaorden(getContext(),listaordenes);
        pe_htorclvlistaordenes.setAdapter(adapter);
        adapter.setOnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(DATOS.VARGOB_ID_ORDEN, listaordenes.get(pe_htorclvlistaordenes.getChildAdapterPosition(v)).getIdorden());
                editor.apply();
                Toast.makeText(getContext(), listaordenes.get(pe_htorclvlistaordenes.getChildAdapterPosition(v)).getIdorden(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.action_nav_historialordenes_to_nav_detallehistorial);
            }
        });
    }
}