package mx.com.encargalo.repartidor.Inicio_sesion.ui.Pedidos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import mx.com.encargalo.repartidor.Adapters.mio_adaprvChatAdapter;
import mx.com.encargalo.repartidor.Entidades.mio_mdlMensajeOrden;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

import static android.content.Context.MODE_PRIVATE;

public class mio_frgconversacion extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    RecyclerView rvChat;
    mio_adaprvChatAdapter adapter;
    ArrayList<mio_mdlMensajeOrden> mio_mdlMensajeOrdenArrayList;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    StringRequest stringRequest;
    EditText edtMensaje;
    ImageView btnSend;
    String responsesave="123";
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mio_frgconversacion, container, false);
        request = Volley.newRequestQueue(getContext());
        edtMensaje = view.findViewById(R.id.is_cedtescribiraqui);
        btnSend = view.findViewById(R.id.mio_btn_send);
        rvChat = view.findViewById(R.id.rv_mio_mensajes_chat);
        mio_mdlMensajeOrdenArrayList = new ArrayList<>();

        sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtMensaje.getText().length()>0){
                    regMensaje();

//                    SharedPreferences conmensaje= getContext().getSharedPreferences("ConfiguracionNotificaciones",0);
//                    int activarnotuno = conmensaje.getInt("Notuno",1);
//                    if (activarnotuno==0){
//                        enviarNotificaionmensajes();
//                    }

                }else {
                    Toast.makeText(getContext(), "Ingrese un mensaje", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cargarChat();
        return view;
    }

    private void cargarChat() {
        final Thread miHilo=new Thread(){
            public void run(){
                try {
                    while (true){
                        Thread.sleep(1500);
                        cargarWSMensajes();
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }


        };
        miHilo.start();
    }

    private void cargarWSMensajes() {
        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
        String url = DATOS.IP_SERVER+"c_consultar_mensajes_chat_x_orden.php?id_Orden="+sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,"");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    private void regMensaje() {
        String url = DATOS.IP_SERVER+"a_nuevo_mensaje_chat.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                edtMensaje.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fallo al enviar: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("id_Orden",sharedPreferences.getString(DATOS.VARGOB_ID_ORDEN,""));
                params.put("id_usuario",sharedPreferences.getString(DATOS.VARGOB_ID_USUARIO,""));
                params.put("mensaje",edtMensaje.getText().toString());
                params.put("imagen","");
                return params;
            }
        };
        request.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Chat en blanco", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
        if (!responsesave.equals(response.toString())){

            mio_mdlMensajeOrden mensaje = null;
            JSONArray json = response.optJSONArray("consulta");

            mio_mdlMensajeOrdenArrayList = new ArrayList<>();
                for (int i = 0; i<json.length();i++){
                    mensaje = new mio_mdlMensajeOrden();
                    JSONObject jsonObject = null;

                    jsonObject = json.getJSONObject(i);
                    mensaje.setMio_idChat(jsonObject.optInt("idChat"));
                    mensaje.setMio_idUsuario(jsonObject.optInt("idUsuario"));
                    mensaje.setMio_rolUsuario(jsonObject.optString("ruNombre"));
                    mensaje.setMio_nomUsuario(jsonObject.optString("perNombres"));
                    mensaje.setMio_imgUsuario(jsonObject.optString("usuImagen"));
                    mensaje.setMio_fechaMensaje(jsonObject.optString("menFechaEnvio"));
                    mensaje.setMio_contenidoMensaje(jsonObject.optString("menContenido"));
                    mio_mdlMensajeOrdenArrayList.add(mensaje);

                }

                mostrarDatos();

                responsesave = response.toString();
        }
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "No existe mensajes", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDatos() {
        try {
            LinearLayoutManager manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,true);
            rvChat.setLayoutManager(manager);
            adapter = new mio_adaprvChatAdapter(mio_mdlMensajeOrdenArrayList,getContext());
            rvChat.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

    }

//    private void enviarNotificaionmensajes() {
//        SharedPreferences conmensaje1= getContext().getSharedPreferences("ConfiguracionNotificaciones",0);
//        String tipo = conmensaje1.getString("Tipouno","");
//        String url = DATOS.IP_SERVER+"e_enviar_notificacion_mensaje_soporte.php?id_rn_idpersona=11111111&id_rn_orden=1&rn_tiponoti="+tipo+"&rn_descnoti="+edtMensaje.getText().toString();
//        url = url.replace(" ", "%20");
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                    Toast.makeText(getContext(), "Notificacion Enviada", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(),"No se pudo enviar"+ error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        request.add(jsonObjectRequest);
//    }
}