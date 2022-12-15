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

import mx.com.encargalo.repartidor.Adapters.sp_Adaptervideodemo;
import mx.com.encargalo.repartidor.Entidades.sp_Entidadvideodemo;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sp_frgdetallevideodemostrativo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sp_frgdetallevideodemostrativo extends Fragment {

    RecyclerView rclvvideodemo;
    ArrayList<sp_Entidadvideodemo> listavideodemo;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sp_frgdetallevideodemostrativo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment so_detallevideodemostrativo.
     */
    // TODO: Rename and change types and number of parameters
    public static sp_frgdetallevideodemostrativo newInstance(String param1, String param2) {
        sp_frgdetallevideodemostrativo fragment = new sp_frgdetallevideodemostrativo();
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
        View view= inflater.inflate(R.layout.fragment_sp_frgdetallevideodemostrativo, container, false);
        listavideodemo=new ArrayList<>();
        rclvvideodemo = view.findViewById(R.id.rclv_listavideodemo);

        rclvvideodemo.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rclvvideodemo.setHasFixedSize(true);
        request= Volley.newRequestQueue(getContext());

        mostrarlistavideosdemo();
        return view;
    }

    private void mostrarlistavideosdemo() {
        progress= new ProgressDialog(getContext());
        progress.setMessage("Consulta datos");
        progress.show();
        String url= DATOS.IP_SERVER+"c_consultar_video_demostrativo.php";
        url=url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sp_Entidadvideodemo miusuario=null;
                JSONArray json=response.optJSONArray("videosdemo");

                try{
                    listavideodemo.clear();
                    for(int i=0; i<json.length();i++){
                        miusuario=new sp_Entidadvideodemo();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);
                        miusuario.setVideotitulo(jsonObject.optString("videTitulo"));
                        miusuario.setVideodescri(jsonObject.optString("videDescripcion"));
                        miusuario.setVideoURL(jsonObject.optString("videURL"));
                        listavideodemo.add(miusuario);
                    }
                    progress.hide();
                    sp_Adaptervideodemo adapterIndicadores=new sp_Adaptervideodemo(listavideodemo);
                    rclvvideodemo.setAdapter(adapterIndicadores);
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