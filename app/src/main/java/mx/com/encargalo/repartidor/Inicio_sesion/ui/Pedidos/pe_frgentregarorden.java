package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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

public class pe_frgentregarorden extends Fragment {
    Button pe_etobtncobrar;
    TextView pe_etotxtidorden_fecha_hora, pe_etotxtnombretienda, pe_etotxtdireccion;
    Dialog dialogyes_no;
    View viewinterno;

    Marker ubicacionrt, destino;
    LatLng coordenadasO = null;

    GoogleMap mMap;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "No funciona", Toast.LENGTH_SHORT).show();
            }else {
            }
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location l) {
                    coordenadasO = new LatLng(l.getLatitude(),l.getLongitude());
                    if(ubicacionrt == null){
                        ubicacionrt = mMap.addMarker(new MarkerOptions().draggable(true).position(coordenadasO)
                                .title("Repartidor").icon(BitmapDescriptorFactory.fromResource(R.drawable.pe_imgrepartidorgps)));
                        CameraUpdate myUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadasO, 20);
                        mMap.animateCamera(myUbicacion);
                    }
                    ubicacionrt.setPosition(coordenadasO);
                }
            });
//            try {
//                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                List<Address> list = geocoder.getFromLocation(
//                        l.getLatitude(),l.getLongitude(), 1);
//                if (!list.isEmpty()) {
//                    Address DirCalle = list.get(0);
//                    Toast.makeText(getContext(), DirCalle.getAddressLine(0), Toast.LENGTH_SHORT).show();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pe_frgentregarorden, container, false);

        request = Volley.newRequestQueue(getContext());

        dialogyes_no = new Dialog(getContext());
        pe_etobtncobrar = view.findViewById(R.id.pe_etobtncobrar);

        pe_etotxtidorden_fecha_hora = view.findViewById(R.id.pe_etotxtidorden_fecha_hora);
        pe_etotxtnombretienda = view.findViewById(R.id.pe_etotxtnombretienda);
        pe_etotxtdireccion = view.findViewById(R.id.pe_etotxtdireccion);

        pe_etobtncobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewinterno = v;
                dialogyes_no.setContentView(R.layout.pe_dialogconfirmarcobro);
                dialogyes_no.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogyes_no.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogyes_no.setCancelable(false);
                Button pe_dynbtnno = dialogyes_no.findViewById(R.id.pe_dynbtnno);
                Button pe_dynbtnsi = dialogyes_no.findViewById(R.id.pe_dynbtnsi);
                pe_dynbtnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogyes_no.dismiss();
                        Navigation.findNavController(viewinterno).navigate(R.id.action_nav_entregarpedido_to_nav_cobrarpedido);
                    }
                });
                pe_dynbtnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogyes_no.dismiss();
                    }
                });
                dialogyes_no.show();
            }
        });
        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        me_modgetcoordenadas(sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));
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
        String APIREST_URL = DATOS.IP_SERVER+ "c_coordenadas_de_orden_entregada.php?"+
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

                        pe_etotxtidorden_fecha_hora.setText("ID ORDEN : "+idOrden+"         "+response.optString("HoraFecha"));
                        pe_etotxtnombretienda.setText("Nombre cliente : "+response.optString("Nombre"));
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> list = null;
                        try {
                            list = geocoder.getFromLocation(
                                    Double.parseDouble(response.optString("Latitud")),
                                    Double.parseDouble(response.optString("Longitud")),
                                    1);
                            if (!list.isEmpty()) {
                                Address DirCalle = list.get(0);

                                pe_etotxtdireccion.setText("Dirección : "+DirCalle.getAddressLine(0));
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
}