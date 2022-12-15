//Creado por Briceño Malpartida Douglas Igancio - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.com.encargalo.repartidor.Adapters.sp_AdapterPreguntasFrecuentes;
import mx.com.encargalo.repartidor.Entidades.sp_EntidadPreguntasFrecuentes;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sp_frgDetallePreguntaFrecuentes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sp_frgDetallePreguntaFrecuentes extends Fragment {

    RecyclerView rclvprefrec;
    ArrayList<sp_EntidadPreguntasFrecuentes> listapregfrec;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sp_frgDetallePreguntaFrecuentes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment so_DetallePreguntaFrecuentes.
     */
    // TODO: Rename and change types and number of parameters
    public static sp_frgDetallePreguntaFrecuentes newInstance(String param1, String param2) {
        sp_frgDetallePreguntaFrecuentes fragment = new sp_frgDetallePreguntaFrecuentes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sp__frgdetalle_pregunta_frecuentes, container, false);
        listapregfrec=new ArrayList<>();
        rclvprefrec = view.findViewById(R.id.rclv_detallepregfrec);

        rclvprefrec.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rclvprefrec.setHasFixedSize(true);
        request= Volley.newRequestQueue(getContext());

        Mostrarlistapreguntafrecuentes();
        return view;
    }

    private void Mostrarlistapreguntafrecuentes() {

        progress= new ProgressDialog(getContext());
        progress.setMessage("Consulta datos");
        progress.show();
        String url= DATOS.IP_SERVER+"c_consultar_preg_frecuente.php?idclaseper=2";
        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sp_EntidadPreguntasFrecuentes miusuario=null;
                JSONArray json=response.optJSONArray("preguntafrec");

                try{
                    listapregfrec.clear();
                    for(int i=0; i<json.length();i++){
                        miusuario=new sp_EntidadPreguntasFrecuentes();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);
                        miusuario.setPregunta(jsonObject.optString("prfrPregunta"));
                        miusuario.setRespuesta(jsonObject.optString("prfrRespuesta"));
                        miusuario.setURL_video(jsonObject.optString("prfrURLVideo"));
                        miusuario.setURL_img(jsonObject.optString("prfrImagen"));
                        listapregfrec.add(miusuario);
                    }
                    progress.hide();
                    sp_AdapterPreguntasFrecuentes adapterpregfre=new sp_AdapterPreguntasFrecuentes(listapregfrec);
                    rclvprefrec.setAdapter(adapterpregfre);
                }catch (JSONException e){
                    e.printStackTrace();
                    progress.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al consultar"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
}