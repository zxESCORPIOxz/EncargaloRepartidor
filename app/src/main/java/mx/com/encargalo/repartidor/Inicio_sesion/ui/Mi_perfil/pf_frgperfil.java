// CÓDIGO: KEVIN DURAN Y JEAN PIERRE LAURENTE
// WEB SERVICE: JEAN LAURENTE
// UNIVERSIDAD CONTINENTAL
// 11/2022

package mx.com.encargalo.repartidor.Inicio_sesion.ui.Mi_perfil;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.com.encargalo.repartidor.Adapters.pe_adplistaproductos;
import mx.com.encargalo.repartidor.Entidades.pe_claseproducto_lista;
import mx.com.encargalo.repartidor.Inicio_sesion.MainActivity;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


public class pf_frgperfil extends Fragment {
    Button  pf_pfbtnmodregistrodevehiculo,
            pf_pfbtntienda,
            pf_pfbtnmodnombre,
            pf_pfbtnmodapellidos,
            pf_pfbtnmodnumero,
            pf_pfbtnmodregistrodelicencia,
            pf_pfbtnmodregistrodeantecedentes,
            pf_pfbtnmodimagen,
            pf_pfbtnmodtargetadepropiedad,
            pf_pfbtnvehiculo,
            pf_pfbtnplaca;

    TextView pf_pfedtdireccion,
            re_reedtcodtienda,
            re_reedtnombre,
            re_reedtapellid,
            re_reedttelef,
            pf_pfedtnombre,
            pf_pfedtapellidos,
            pf_pfedtnumero,
            pf_pfedtmovilidad,
            pf_pfedttienda,
            pf_prftxtnombre,
            pf_pfedtplaca,
            re_reedtnombrevehiculo2,
            re_reeditplaca,
            pf_pftxtESTADO1,
            pf_pftxtESTADO2,
            pf_pftxtESTADO3,
            pf_pftxtESTADO4;

    Dialog  dialog,
            dialognomb,
            dialogapell,
            dialogtelefono,
            dialogvehiculo,
            dialogplac;

    Spinner re_respntipo2;

    ImageView pf_prfimgvwfoto;
    SharedPreferences sharedPreferences;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;
    boolean cimg=false;
    private Uri imageUri;
    Bitmap bitmapimgselecionada;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pf_frgperfil, container, false);
        request= Volley.newRequestQueue(getContext());

        pf_pfbtntienda = view.findViewById(R.id.pf_pfbtntienda);

        pf_pfbtnmodnombre = view.findViewById(R.id.pf_pfbtnmodnombre);
        pf_pfbtnmodapellidos = view.findViewById(R.id.pf_pfbtnmodapellidos);
        pf_pfbtnmodnumero = view.findViewById(R.id.pf_pfbtnmodnumero);
        pf_pfbtnvehiculo = view.findViewById(R.id.pf_pfbtnvehiculo);
        pf_pfbtnplaca = view.findViewById(R.id.pf_pfbtnplaca);

        pf_prftxtnombre = view.findViewById(R.id.pf_prftxtnombre);
        pf_pftxtESTADO1 = view.findViewById(R.id.pf_pftxtESTADO1);
        pf_pftxtESTADO2 = view.findViewById(R.id.pf_pftxtESTADO2);
        pf_pftxtESTADO3 = view.findViewById(R.id.pf_pftxtESTADO3);
        pf_pftxtESTADO4 = view.findViewById(R.id.pf_pftxtESTADO4);
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
        me_modgetEstados(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
        me_modgetTienda(sharedPreferences.getString(DATOS.VARGOB_ID_REPARTIDOR,"X"));

        //Boton Imagen
        pf_prfimgvwfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirAccion();
            }
        });
        pf_pfbtnmodimagen = view.findViewById(R.id.pf_pfbtnmodimagen);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pf_pfbtnmodimagen.getLayoutParams();
        params.height = 0;
        pf_pfbtnmodimagen.setLayoutParams(params);
        pf_pfbtnmodimagen.setVisibility(View.INVISIBLE);
        pf_pfbtnmodimagen.setEnabled(false);


        //Cargar Imagen
        pf_pfbtnmodimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = DATOS.IP_SERVER+ "m_imagen_usuario_repartidor.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), "Imagen de perfil modificada", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Imagen de perfil no se pudo modificar", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SharedPreferences sharedPreferences =
                                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put("usu_Imagen", getStringImagen(bitmapimgselecionada) );
                        params.put("id_DocumentoPersona", sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""));
                        return params;
                    }
                };
                request.add(stringRequest);
            }
        });



        pf_pfbtnmodregistrodevehiculo = view.findViewById(R.id.pf_pfbtnmodregistrodevehiculo);

        pf_pfbtnmodregistrodevehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tipodocumento","registrovehiculo");
                editor.apply();

                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodelicencia = view.findViewById(R.id.pf_pfbtnmodregistrodelicencia);
        pf_pfbtnmodregistrodelicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tipodocumento","registrodelicencia");
                editor.apply();
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodregistrodeantecedentes = view.findViewById(R.id.pf_pfbtnmodregistrodeantecedentes);
        pf_pfbtnmodregistrodeantecedentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tipodocumento","registrodeantecedentes");
                editor.apply();
                Navigation.findNavController(v).navigate(R.id.action_nav_miperfil_to_nav_cargardocumento);
            }
        });
        pf_pfbtnmodtargetadepropiedad = view.findViewById(R.id.pf_pfbtnmodtargetadepropiedad);
        pf_pfbtnmodtargetadepropiedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tipodocumento","rgetadepropiedad");
                editor.apply();
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
                        if(re_reedtcodtienda.getText().length() != 0){
                            me_modsetTienda(sharedPreferences.getString(DATOS.VARGOB_ID_REPARTIDOR,""),re_reedtcodtienda.getText().toString());
                        }else {
                            Toast.makeText(getContext(), "Ingrese el codigo de tienda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        pf_pfbtnmodnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialognomb = new Dialog(getContext());
                dialognomb.setContentView(R.layout.re_dialogrepnombre);
                dialognomb.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialognomb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialognomb.setCancelable(false);
                re_reedtnombre = dialognomb.findViewById(R.id.re_reedtnombre);
                ImageView re_rebtnclose1 = dialognomb.findViewById(R.id.re_rebtnclose1);
                re_rebtnclose1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialognomb.dismiss();
                    }
                });
                Button btn_Act = dialognomb.findViewById(R.id.re_rebtnactualizar);
                btn_Act.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modNombre(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""),re_reedtnombre.getText().toString());

                    }
                });
                dialognomb.show();
            }
        });

        pf_pfbtnmodapellidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogapell = new Dialog(getContext());
                dialogapell.setContentView(R.layout.re_dialogrepapellido);
                dialogapell.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogapell.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogapell.setCancelable(false);
                re_reedtapellid = dialogapell.findViewById(R.id.re_reedtapellid);
                ImageView re_rebtnclose2 = dialogapell.findViewById(R.id.re_rebtnclose2);
                re_rebtnclose2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogapell.dismiss();
                    }
                });
                Button btn_Act2 = dialogapell.findViewById(R.id.re_rebtnactualizar2);
                btn_Act2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modApellido(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""),re_reedtapellid.getText().toString());
                    }
                });
                dialogapell.show();
            }
        });

        pf_pfbtnmodnumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogtelefono = new Dialog(getContext());
                dialogtelefono.setContentView(R.layout.re_dialogreptelefono);
                dialogtelefono.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogtelefono.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogtelefono.setCancelable(false);
                re_reedttelef = dialogtelefono.findViewById(R.id.re_reedttelef);
                ImageView re_rebtnclose3 = dialogtelefono.findViewById(R.id.re_rebtnclose3);
                re_rebtnclose3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogtelefono.dismiss();
                    }
                });
                Button btn_Act3 = dialogtelefono.findViewById(R.id.re_rebtnactualizar3);
                btn_Act3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modTelefono(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""),re_reedttelef.getText().toString());
                    }
                });
                dialogtelefono.show();
            }
        });

        pf_pfbtnvehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogvehiculo = new Dialog(getContext());
                dialogvehiculo.setContentView(R.layout.re_dialogeditvehiculo);
                dialogvehiculo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogvehiculo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogvehiculo.setCancelable(false);
                re_respntipo2 = dialogvehiculo.findViewById(R.id.re_respntipo2);
                re_reedtnombrevehiculo2 = dialogvehiculo.findViewById(R.id.re_reedtnombrevehiculo2);
                ImageView re_rebtnclose4 = dialogvehiculo.findViewById(R.id.re_rebtnclose4);
                re_rebtnclose4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogvehiculo.dismiss();
                    }
                });
                Button btn_Act4 = dialogvehiculo.findViewById(R.id.re_rebtnactualizar4);
                btn_Act4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modvehiculo(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""), re_respntipo2.getSelectedItem().toString(), re_reedtnombrevehiculo2.getText().toString());
                    }
                });
                dialogvehiculo.show();
            }
        });

        pf_pfbtnplaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogplac = new Dialog(getContext());
                dialogplac.setContentView(R.layout.re_dialogrepeditplaca);
                dialogplac.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogplac.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogplac.setCancelable(false);
                re_reeditplaca = dialogplac.findViewById(R.id.re_reeditplaca);
                ImageView re_rebtnclose5 = dialogplac.findViewById(R.id.re_rebtnclose5);
                re_rebtnclose5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogplac.dismiss();
                    }
                });
                Button btn_Act5 = dialogplac.findViewById(R.id.re_rebtnactualizar5);
                btn_Act5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        me_modPlaca(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""),re_reeditplaca.getText().toString());
                    }
                });
                dialogplac.show();
            }
        });

        return view;
    }

    //Cargar camara o galeria

    public void elegirAccion(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pf_dialog_camara_galeria);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView close = dialog.findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button camara = dialog.findViewById(R.id.btn_Camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                    estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                        dialog.dismiss();
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PackageManager.PERMISSION_GRANTED);
                    }
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            PackageManager.PERMISSION_GRANTED);
                }
            }
        });
        Button galeria = dialog.findViewById(R.id.btn_Galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                seleccionarImagen();
            }
        });
        dialog.show();
    }

    public void tomarFoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, TOMAR_FOTO);
    }
    public void seleccionarImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELEC_IMAGEN);
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
                dialog.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modNombre(final String id_DocumentoPersona, String per_Nombres){
        String APIREST_URL = DATOS.IP_SERVER+ "m_nombre_persona.php?"+
                "id_DocumentoPersona=" + id_DocumentoPersona+
                "&per_Nombres=" + per_Nombres;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pf_pfedtnombre.setText(response.optString("Nombre"));
               //pf_pfbtnmodnombre.setEnabled(false);
                dialognomb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Nombre Actualizado", Toast.LENGTH_SHORT).show();
                dialognomb.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modApellido(final String id_DocumentoPersona, String per_Apellidos){
        String APIREST_URL = DATOS.IP_SERVER+ "m_apellidos_persona.php?"+
                "id_DocumentoPersona=" + id_DocumentoPersona+
                "&per_Apellidos=" + per_Apellidos;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pf_pfedtapellidos.setText(response.optString("Nombre"));
                //pf_pfedtapellidos.setEnabled(false);
                dialogapell.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Apellido Actualizado", Toast.LENGTH_SHORT).show();
                dialogapell.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modTelefono(final String id_DocumentoPersona, String per_NumeroCelular){
        String APIREST_URL = DATOS.IP_SERVER+ "m_numerocelular_persona.php?"+
                "id_DocumentoPersona=" + id_DocumentoPersona+
                "&per_NumeroCelular=" + per_NumeroCelular;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pf_pfedtnumero.setText(response.optString("Nombre"));
                //pf_pfedtnumero.setEnabled(false);
                dialogtelefono.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Telefono Actualizado", Toast.LENGTH_SHORT).show();
                dialogtelefono.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modvehiculo(final String id_DocumentoPersona, String rep_TipoVehiculo, String rep_NombreVehiculo){
        String APIREST_URL = DATOS.IP_SERVER+ "m_vehiculo_persona.php?"+
                "id_DocumentoPersona=" + id_DocumentoPersona+
                "&rep_TipoVehiculo=" + rep_TipoVehiculo+
                "&rep_NombreVehiculo=" + rep_NombreVehiculo;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pf_pfedtnumero.setText(response.optString("Nombre"));
                //pf_pfedtnumero.setEnabled(false);
                dialogvehiculo.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Vehiculo Actualizado", Toast.LENGTH_SHORT).show();
                dialogvehiculo.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
            }
        });
        request.add(jsonObjectRequest);
    }

    public void me_modPlaca(final String id_DocumentoPersona, String rep_Placa){
        String APIREST_URL = DATOS.IP_SERVER+ "m_placa_repartidor.php?"+
                "id_DocumentoPersona=" + id_DocumentoPersona+
                "&rep_Placa=" + rep_Placa;
        APIREST_URL = APIREST_URL.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIREST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pf_pfedtnumero.setText(response.optString("Nombre"));
                //pf_pfedtnumero.setEnabled(false);
                dialogplac.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Placa Actualizada", Toast.LENGTH_SHORT).show();
                dialogplac.dismiss();
                me_modgetPerfil(sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,"X"));
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
                pf_pfedttienda.setEnabled(false);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pf_pfbtntienda.getLayoutParams();
                params.height = 0;
                params.width = 0;
                pf_pfbtntienda.setLayoutParams(params);
                pf_pfbtntienda.setVisibility(View.INVISIBLE);
                pf_pfbtntienda.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), DATOS.NO_ENCONTRADO, Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK){
                if(requestCode == SELEC_IMAGEN){
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if(requestCode == TOMAR_FOTO){
                    CropImage.activity(imageUri)
                            .setAspectRatio(1, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                    //Croped image received
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK){
                        Uri resultUri = result.getUri();
                        imageUri = resultUri;
                        pf_prfimgvwfoto.setImageURI(resultUri);
                        Bitmap bm = ((BitmapDrawable)pf_prfimgvwfoto.getDrawable()).getBitmap();
                        bitmapimgselecionada=Bitmap.createScaledBitmap(bm, 100, 100, true);
                        cimg=true;
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pf_pfbtnmodimagen.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        pf_pfbtnmodimagen.setLayoutParams(params);
                        pf_pfbtnmodimagen.setVisibility(View.VISIBLE);
                        pf_pfbtnmodimagen.setEnabled(true);
                    }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        Exception error = result.getError();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                                //Validación si estado es INACTIVO
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


    public void me_modgetEstados(String Documento){
        String APIREST_URL = DATOS.IP_SERVER+ "c_documentos_repartidor.php?"+
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

//                                pf_pftxtESTADO1.setText(myjsonObject.getString("repDocumentoVehiculoEstado"));
//                                pf_pftxtESTADO1.setEnabled(false);
//                                pf_pftxtESTADO2.setText(myjsonObject.getString("repLicenciaEstado"));
//                                pf_pftxtESTADO2.setEnabled(false);
//                                pf_pftxtESTADO3.setText(myjsonObject.getString("repAntecedentesEstado"));
//                                pf_pftxtESTADO3.setEnabled(false);
//                                pf_pftxtESTADO4.setText(myjsonObject.getString("repTarjetaPropiedadEstado"));
//                                pf_pftxtESTADO4.setEnabled(false);

                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.putString(DATOS.VARGOB_ESTADO_REPARIDOR,myjsonObject.getString("repDocumentoVehiculoEstado"));
                                //Validación si estado es INACTIVO
                                if(myjsonObject.getString("repDocumentoVehiculoEstado").equals("INACTIVO")){
                                    pf_pftxtESTADO1.append("INACTIVO");
                                    pf_pftxtESTADO1.setTextColor(Color.RED);
                                }else if(myjsonObject.getString("repDocumentoVehiculoEstado").equals("EN REVISION")){
                                    pf_pftxtESTADO1.append("EN REVISION");
                                    pf_pftxtESTADO1.setTextColor(Color.BLUE);
                                }else if(myjsonObject.getString("repDocumentoVehiculoEstado").equals("ACTIVO")){
                                    pf_pftxtESTADO1.append("ACTIVO");
                                    pf_pftxtESTADO1.setTextColor(Color.GREEN);
                                }

                                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                editor2.putString(DATOS.VARGOB_ESTADO_REPARIDOR,myjsonObject.getString("repLicenciaEstado"));
                                //Validación si estado es INACTIVO
                                if(myjsonObject.getString("repLicenciaEstado").equals("INACTIVO")){
                                    pf_pftxtESTADO2.append("INACTIVO");
                                    pf_pftxtESTADO2.setTextColor(Color.RED);
                                }else if(myjsonObject.getString("repLicenciaEstado").equals("EN REVISION")){
                                    pf_pftxtESTADO2.append("EN REVISION");
                                    pf_pftxtESTADO2.setTextColor(Color.BLUE);
                                }else if(myjsonObject.getString("repLicenciaEstado").equals("ACTIVO")){
                                    pf_pftxtESTADO2.append("ACTIVO");
                                    pf_pftxtESTADO2.setTextColor(Color.GREEN);
                                }

                                SharedPreferences.Editor editor3 = sharedPreferences.edit();
                                editor3.putString(DATOS.VARGOB_ESTADO_REPARIDOR,myjsonObject.getString("repAntecedentesEstado"));
                                //Validación si estado es INACTIVO
                                if(myjsonObject.getString("repAntecedentesEstado").equals("INACTIVO")){
                                    pf_pftxtESTADO3.append("INACTIVO");
                                    pf_pftxtESTADO3.setTextColor(Color.RED);
                                }else if(myjsonObject.getString("repAntecedentesEstado").equals("EN REVISION")){
                                    pf_pftxtESTADO3.append("EN REVISION");
                                    pf_pftxtESTADO3.setTextColor(Color.BLUE);
                                }else if(myjsonObject.getString("repAntecedentesEstado").equals("ACTIVO")){
                                    pf_pftxtESTADO3.append("ACTIVO");
                                    pf_pftxtESTADO3.setTextColor(Color.GREEN);
                                }

                                SharedPreferences.Editor editor4 = sharedPreferences.edit();
                                editor4.putString(DATOS.VARGOB_ESTADO_REPARIDOR,myjsonObject.getString("repTarjetaPropiedadEstado"));
                                //Validación si estado es INACTIVO
                                if(myjsonObject.getString("repTarjetaPropiedadEstado").equals("INACTIVO")){
                                    pf_pftxtESTADO4.append("INACTIVO");
                                    pf_pftxtESTADO4.setTextColor(Color.RED);
                                }else if(myjsonObject.getString("repTarjetaPropiedadEstado").equals("EN REVISION")){
                                    pf_pftxtESTADO4.append("EN REVISION");
                                    pf_pftxtESTADO4.setTextColor(Color.BLUE);
                                }else if(myjsonObject.getString("repTarjetaPropiedadEstado").equals("ACTIVO")){
                                    pf_pftxtESTADO4.append("ACTIVO");
                                    pf_pftxtESTADO4.setTextColor(Color.GREEN);
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