package mx.com.encargalo.repartidor.Inicio_sesion.ui.Soporte;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sp_frgDetallepreguntas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sp_frgDetallepreguntas extends Fragment {

    TextView txt_so_02_3_PregFrecuentes, txt_so_02_3_Pregunta1, txt_so_02_3_preg1_content;
    ImageView img_so_02_3_preg1_image;
    VideoView View_so_02_3_preg1;

    YouTubePlayerView ytpv_vidpregfre;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sp_frgDetallepreguntas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sp_frg02_3.
     */
    // TODO: Rename and change types and number of parameters
    public static sp_frgDetallepreguntas newInstance(String param1, String param2) {
        sp_frgDetallepreguntas fragment = new sp_frgDetallepreguntas();
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


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sp_frgdetallepreguntas, container, false);

        Bundle data = getArguments();

        txt_so_02_3_Pregunta1 = view.findViewById(R.id.txt_so_02_3_Pregunta1);
        txt_so_02_3_preg1_content = view.findViewById(R.id.txt_so_02_3_preg1_content);
        img_so_02_3_preg1_image = view.findViewById(R.id.img_so_02_3_preg1_image);
        View_so_02_3_preg1 = view.findViewById(R.id.View_so_02_3_preg1);
        ytpv_vidpregfre=view.findViewById(R.id.ytpv_videopregfrec);

        if (data != null){
            String preg = data.getString("pregunta");
            String resp = data.getString("respuesta");
            String url_img = data.getString("URL_img");
            String url_vid = data.getString("URL_vid");

            txt_so_02_3_Pregunta1.setText(preg);
            txt_so_02_3_preg1_content.setText(resp);

            if (url_vid.equals("")) {

                Toast.makeText(getContext(), "No existe video", Toast.LENGTH_SHORT).show();
            }else{
                String urlcom=url_vid;
                urlcom=urlcom.substring(0,23);

                if (urlcom.equals("vidPreguntasFrecuentes/")){
                    String url =  DATOS.IP_SERVER +  url_vid;
                    View_so_02_3_preg1.setVisibility(View.VISIBLE);
                    ytpv_vidpregfre.setVisibility(View.INVISIBLE);
                    ytpv_vidpregfre.setMinimumHeight(0);
                    View_so_02_3_preg1.setVideoURI(Uri.parse(url));
                    View_so_02_3_preg1.start();
                    MediaController mediaController=new MediaController(getContext());
                    View_so_02_3_preg1.setMediaController(mediaController);
                    mediaController.setAnchorView(View_so_02_3_preg1);
                    View_so_02_3_preg1.pause();
                }else{
                    ytpv_vidpregfre.setVisibility(View.VISIBLE);
                    View_so_02_3_preg1.setVisibility(View.INVISIBLE);
                    View_so_02_3_preg1.setMinimumHeight(0);
                    String id=String.valueOf(url_vid);
                    id=id.replace("https://www.youtube.com/watch?v=", "");
                    final String finalId = id;
                    ytpv_vidpregfre.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            //youTubePlayer.loadVideo(finalId,0);
                            youTubePlayer.loadVideo(finalId,0);
                            youTubePlayer.pause();
                        }
                    });
                }
            }

        }
        else{
            Toast.makeText(getContext(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}