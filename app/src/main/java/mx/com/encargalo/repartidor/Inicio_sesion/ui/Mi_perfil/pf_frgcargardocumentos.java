package mx.com.encargalo.repartidor.Inicio_sesion.ui.Mi_perfil;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.repartidor.Inicio_sesion.MainActivity;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;

public class pf_frgcargardocumentos extends Fragment {
    Button pf_rubtnRegistrar;

    String tipodocumento;

    Dialog  dialog;

    ImageView pf_prfimgvwfoto;

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

        View view = inflater.inflate(R.layout.fragment_pf_frgcargardocumentos, container, false);
        request= Volley.newRequestQueue(getContext());

        SharedPreferences sharedPreferences =
        getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, Context.MODE_PRIVATE);
        tipodocumento = sharedPreferences.getString("tipodocumento","");

        pf_prfimgvwfoto = view.findViewById(R.id.pf_prfimgvwfoto);

        //Boton Imagen
        pf_prfimgvwfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirAccion1();
            }
        });
        pf_rubtnRegistrar = view.findViewById(R.id.pf_rubtnRegistrarVehiculo);

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pf_rubtnRegistrar.getLayoutParams();
//        params.height = 0;
//        pf_rubtnRegistrar.setLayoutParams(params);
//        pf_rubtnRegistrar.setVisibility(View.INVISIBLE);
//        pf_rubtnRegistrar.setEnabled(false);

        //Cargar DocumentoVehiculo
        pf_rubtnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myURL = DATOS.IP_SERVER+ "m_documentovehiculo_repartidor.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), "Documento Vehiculo Registrado", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Documento Vehiculo no se registró", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SharedPreferences sharedPreferences =
                                getContext().getSharedPreferences(DATOS.SHAREDPREFERENCES, MODE_PRIVATE);
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put("rep_DocumentoVehiculo", getStringImagen(bitmapimgselecionada) );
                        params.put("id_DocumentoPersona", sharedPreferences.getString(DATOS.VARGOB_ID_PERSONA,""));
                        return params;
                    }
                };
                request.add(stringRequest);

                Navigation.findNavController(v).navigate(R.id.action_nav_cargardocumento_to_nav_docsubido);
            }
        });



        return view;
    }

    //Cargar camara o galeria

    public void elegirAccion1(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pf_dialog_camara_galeria1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView close = dialog.findViewById(R.id.btn_close1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button camara = dialog.findViewById(R.id.btn_Camara1);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                    estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                        dialog.dismiss();
                        tomarFoto1();
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
        Button galeria = dialog.findViewById(R.id.btn_Galeria1);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                seleccionarImagen1();
            }
        });
        dialog.show();
    }

    public void tomarFoto1() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, TOMAR_FOTO);
    }

    public void seleccionarImagen1() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELEC_IMAGEN);
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
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pf_rubtnRegistrar.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        pf_rubtnRegistrar.setLayoutParams(params);
                        pf_rubtnRegistrar.setVisibility(View.VISIBLE);
                        pf_rubtnRegistrar.setEnabled(true);
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

}