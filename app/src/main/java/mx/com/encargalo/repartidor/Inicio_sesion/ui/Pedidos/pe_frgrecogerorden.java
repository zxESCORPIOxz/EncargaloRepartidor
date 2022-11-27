package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class pe_frgrecogerorden extends Fragment {
    Button pe_rgobtnrecoger,pe_rgobtnChat;
    TextView pe_rgotxtidorden_fecha_hora, pe_rgotxtnombretienda, pe_rgotxtdireccion;

    Marker ubicacionrt, destino;

    GoogleMap mMap;
    LatLng coordenadasO = null;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }

            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location l) {
                    coordenadasO = new LatLng(l.getLatitude(),l.getLongitude());

                    SharedPreferences sharedPreferences =
                            getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                    m_setcoordenadaTR(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""),
                            l.getLatitude()+"",l.getLongitude()+"");
                    if(ubicacionrt == null){
                        ubicacionrt = mMap.addMarker(new MarkerOptions().draggable(true).position(coordenadasO)
                                .title("Repartidor").icon(BitmapDescriptorFactory.fromResource(R.drawable.pe_imgrepartidorgps)));
                        CameraUpdate myUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadasO, 20);
                        mMap.animateCamera(myUbicacion);
                    }
                    ubicacionrt.setPosition(coordenadasO);
                }
            });
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgrecogerorden, container, false);

        request = Volley.newRequestQueue(getContext());

        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        me_modgetcoordenadas(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));

        pe_rgotxtidorden_fecha_hora = view.findViewById(R.id.pe_rgotxtidorden_fecha_hora);
        pe_rgotxtnombretienda = view.findViewById(R.id.pe_rgotxtnombretienda);
        pe_rgotxtdireccion = view.findViewById(R.id.pe_rgotxtdireccion);

        pe_rgobtnChat = view.findViewById(R.id.pe_rgobtnChat);

        pe_rgobtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_misordenesconversacion);
            }
        });

        pe_rgobtnrecoger = view.findViewById(R.id.pe_rgobtnrecoger);

        pe_rgobtnrecoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_recogerorden_to_nav_confirmarproductos);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void me_modgetcoordenadas(final String idOrden){
        String APIREST_URL = DATOS.IP_SERVER+ "c_coordenadas_de_orden_tienda.php?"+
                "idOrden=" + idOrden;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LatLng coordestino = new LatLng(
                        Double.parseDouble(response.optString("Latitud")),
                        Double.parseDouble(response.optString("Longitud"))
                );
                pe_rgotxtidorden_fecha_hora.setText("ID ORDEN : "+idOrden+"         "+response.optString("HoraFecha"));
                pe_rgotxtnombretienda.setText("Local de venta : "+response.optString("NombreTienda"));
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(
                            Double.parseDouble(response.optString("Latitud")),
                            Double.parseDouble(response.optString("Longitud")),
                            1);
                    if (!list.isEmpty()) {
                        Address DirCalle = list.get(0);
                        pe_rgotxtdireccion.setText("Dirección : "+DirCalle.getAddressLine(0));
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                destino = mMap.addMarker(new MarkerOptions().position(coordestino).draggable(true)
                        .title("Local"));

                CameraUpdate myUbicacion = CameraUpdateFactory.newLatLngZoom(coordestino, 16);
                mMap.animateCamera(myUbicacion);
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


    public void m_setcoordenadaTR(final String idOrden, final String Latitud, final String Longitud){
        String APIREST_URL = DATOS.IP_SERVER+ "m_coordenadas_repartidor.php?"
                +"idOrden=" + idOrden
                +"&Latitud=" + Latitud
                +"&Longitud=" + Longitud;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.GET, APIREST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(stringRequest);
    }
}