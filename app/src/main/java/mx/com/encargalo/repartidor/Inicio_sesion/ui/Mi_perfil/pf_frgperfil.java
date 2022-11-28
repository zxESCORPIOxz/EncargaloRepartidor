package mx.com.encargalo.repartidor.Inicio_sesion.ui.Mi_perfil;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.repartidor.Adapters.pe_adplistaproductos;
import mx.com.encargalo.repartidor.Entidades.pe_claseproducto_lista;
import mx.com.encargalo.repartidor.Inicio_sesion.MainActivity;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


public class pf_frgperfil extends Fragment {
    Button  pf_pfbtnmodregistrodevehiculo,
            pf_pfbtntienda,
            pf_pfbtnmodregistrodelicencia,
            pf_pfbtnmodregistrodeantecedentes,
            pf_pfbtnmodtargetadepropiedad;

    TextView pf_pfedtdireccion,
            re_reedtcodtienda,
            pf_pfedtnombre,
            pf_pfedtapellidos,
            pf_pfedtnumero,
            pf_pfedtmovilidad,
            pf_pfedttienda,
            pf_prftxtnombre,
            pf_pfedtplaca;
    Dialog dialog;

    ImageView pf_prfimgvwfoto;
    SharedPreferences sharedPreferences;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pf_frgperfil, container, false);
        request= Volley.newRequestQueue(getContext());

        pf_pfbtntienda = view.findViewById(R.id.pf_pfbtntienda);

        pf_prftxtnombre = view.findViewById(R.id.pf_prftxtnombre);
        pf_pfedtdireccion = view.findViewById(R.id.pf_pfedtdireccion);
        pf_pfedtnombre = view.findViewById(R.id.pf_pfedtnombre);
        pf_pfedtapellidos = view.findViewById(R.id.pf_pfedtapellidos);
        pf_pfedtnumero = view.findViewById(R.id.pf_pfedtnumero);
        pf_pfedtmovilidad = view.findViewById(R.id.pf_pfedtmovilidad);
        pf_pfedtplaca = view.findViewById(R.id.pf_pfedtplaca);
        pf_pfedttienda = view.findViewById(R.id.pf_pfedttienda);
        pf_prfimgvwfoto = view.findViewById(R.id.pf_prfimgvwfoto);

        sharedPreferences = getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);

        Glide.with(getContext()).load(DATOS.IP_SERVER
                +sharedPreferences.getString(DATOS.VARGOB_IMG_REPARIDOR,"")).into(pf_prfimgvwfoto);
        pf_prftxtnombre.setText(sharedPreferences.getString(DATOS.VARGOB_NAME_REPARIDOR,""));
        me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
        me_modgetTienda(sharedPreferences.getString(DATOS.VARGOB_ID_REPARTIDOR,"X"));

        pf_pfbtnmodregistrodevehiculo = view.findViewById(R.id.pf_pfbtnmodregistrodevehiculo);
        pf_pfbtnmodregistrodevehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodelicencia = view.findViewById(R.id.pf_pfbtnmodregistrodelicencia);
        pf_pfbtnmodregistrodelicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodeantecedentes = view.findViewById(R.id.pf_pfbtnmodregistrodeantecedentes);
        pf_pfbtnmodregistrodeantecedentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodtargetadepropiedad = view.findViewById(R.id.pf_pfbtnmodtargetadepropiedad);
        pf_pfbtnmodtargetadepropiedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtntienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.re_dialogregreptotienda);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                re_reedtcodtienda = dialog.findViewById(R.id.re_reedtcodtienda);
                ImageView re_rebtnclose = dialog.findViewById(R.id.re_rebtnclose);
                re_rebtnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btn_Reg = dialog.findViewById(R.id.re_rebtnregistrartienda);
                btn_Reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modsetTienda(sharedPreferences.getString(DATOS.VARGOB_ID_REPARTIDOR,""),re_reedtcodtienda.getText().toString());
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    public void me_modsetTienda(final String idrepartidor, String idtienda){
        String APIREST_URL = DATOS.IP_SERVER+ "a_contrata_rep.php?"+
                "idrep=" + idrepartidor+
                "&idten=" + idtienda;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pf_pfedttienda.setText(response.optString("Nombre"));
                pf_pfbtntienda.setEnabled(false);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Aun no perteneces a una tienda", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modgetTienda(String idrepartidor){
        String APIREST_URL = DATOS.IP_SERVER+ "c_tienda_repartidor.php?"+
                "idRepartidor=" + idrepartidor;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pf_pfedttienda.setText(response.optString("Nombre"));
                pf_pfbtntienda.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Aun no perteneces a una tienda", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modgetPerfil(String Documento){
        String APIREST_URL = DATOS.IP_SERVER+ "c_perfil_usuario.php?"+
                "id_DocumentoPersona=" + Documento;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonObject = null;
                        try {
                            jsonObject = response.getJSONArray("consulta");
                            //pf_pfedtdireccion.setText(jsonObject.optString("Nombre"));
                            for (int i = 0; i < jsonObject.length(); i++) {
                                JSONObject myjsonObject = jsonObject.getJSONObject(i);
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> list = null;
                                try {
                                    list = geocoder.getFromLocation(
                                            Double.parseDouble(myjsonObject.optString("perUbiLatitud")),
                                            Double.parseDouble(myjsonObject.optString("perUbiLongitud")),
                                            1);
                                    if (!list.isEmpty()) {
                                        Address DirCalle = list.get(0);
                                        pf_pfedtdireccion.setText(DirCalle.getAddressLine(0));
                                        pf_pfedtdireccion.setEnabled(false);
                                    }
                                } catch (IOException e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                pf_pfedtnombre.setText(myjsonObject.getString("perNombres"));
                                pf_pfedtnombre.setEnabled(false);
                                pf_pfedtapellidos.setText(myjsonObject.getString("perApellidos"));
                                pf_pfedtapellidos.setEnabled(false);
                                pf_pfedtnumero.setText(myjsonObject.getString("perNumeroCelular"));
                                pf_pfedtnumero.setEnabled(false);
                                pf_pfedtmovilidad.setText(myjsonObject.getString("repTipoVehiculo"));
                                pf_pfedtmovilidad.setEnabled(false);
                                pf_pfedtplaca.setText(myjsonObject.getString("repPlaca"));
                                pf_pfedtplaca.setEnabled(false);
//                                pf_pfedttienda.setText(myjsonObject.getString("tieNombre"));
                                pf_pfedttienda.setEnabled(false);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(DATOS.VARGOB_ESTADO_REPARIDOR,myjsonObject.getString("repEstado"));
                                if(myjsonObject.getString("repEstado").equals("INACTIVO")){
                                    pf_prftxtnombre.append("\n (INACTIVO) \n Valide los documentos requeridos para laborar como repartidor antes de comensar");
                                    pf_prftxtnombre.setTextColor(Color.RED);
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
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