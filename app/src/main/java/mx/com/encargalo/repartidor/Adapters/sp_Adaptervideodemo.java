package mx.com.encargalo.repartidor.Adapters;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import mx.com.encargalo.repartidor.Entidades.sp_Entidadvideodemo;
import mx.com.encargalo.repartidor.UTIL.DATOS;
import mx.com.repartidor.R;


public class sp_Adaptervideodemo extends RecyclerView.Adapter<sp_Adaptervideodemo.VideosDemotrativoHolder> {
    List<sp_Entidadvideodemo> listavideodemo;

    public sp_Adaptervideodemo(List<sp_Entidadvideodemo> listavideodemo){
        this.listavideodemo = listavideodemo;
    }

    @NonNull
    @Override
    public VideosDemotrativoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_itemdetallevideosdemo,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new VideosDemotrativoHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosDemotrativoHolder holder, int position) {
        holder.txttitulovideodemo.setText(String.valueOf(listavideodemo.get(position).getVideotitulo()));
        holder.txtdescripvideodemo.setText(String.valueOf(listavideodemo.get(position).getVideodescri()));
          holder.videodemo.setVisibility(View.INVISIBLE);
          holder.youTubePlayerView.setVisibility(View.INVISIBLE);
        String urlcom=String.valueOf(listavideodemo.get(position).getVideoURL());

//        urlcom=urlcom.substring(0,32);
//        if (urlcom.equals("https://www.youtube.com/watch?v=")){
//            holder.youTubePlayerView.setVisibility(View.VISIBLE);
//            holder.videodemo.setVisibility(View.INVISIBLE);
//            holder.videodemo.setMinimumHeight(0);
//            String id=String.valueOf(listavideodemo.get(position).getVideoURL());
//            id=id.replace("https://www.youtube.com/watch?v=", "");
//            final String finalId = id;
//            holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                    youTubePlayer.loadVideo(finalId,0);
//                    youTubePlayer.pause();
//                }
//            });
//
//        }else{
//            String url =  Util.RUTA +  listavideodemo.get(position).getVideoURL();
//            holder.videodemo.setVisibility(View.VISIBLE);
//            holder.youTubePlayerView.setVisibility(View.INVISIBLE);
//            holder.youTubePlayerView.setMinimumHeight(0);
//            holder.videodemo.setVideoURI(Uri.parse(url));
//            holder.videodemo.start();
//            MediaController mediaController=new MediaController(holder.videodemo.getContext());
//            holder.videodemo.setMediaController(mediaController);
//            mediaController.setAnchorView(holder.videodemo);
//            holder.videodemo.pause();
//        }

        urlcom=urlcom.substring(0,16);
        if (urlcom.equals("vidDemostracion/")){

            String url = DATOS.IP_SERVER +  listavideodemo.get(position).getVideoURL();
            holder.videodemo.setVisibility(View.VISIBLE);
            holder.youTubePlayerView.setVisibility(View.INVISIBLE);
            holder.youTubePlayerView.setMinimumHeight(0);
            holder.videodemo.setVideoURI(Uri.parse(url));
            holder.videodemo.start();
            MediaController mediaController=new MediaController(holder.videodemo.getContext());
            holder.videodemo.setMediaController(mediaController);
            mediaController.setAnchorView(holder.videodemo);
            holder.videodemo.pause();
        }else{
            holder.youTubePlayerView.setVisibility(View.VISIBLE);
            holder.videodemo.setVisibility(View.INVISIBLE);
            holder.videodemo.setMinimumHeight(0);
            String id=String.valueOf(listavideodemo.get(position).getVideoURL());
            id=id.replace("https://www.youtube.com/watch?v=", "");
            final String finalId = id;
            holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(finalId,0);
                    youTubePlayer.pause();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listavideodemo.size();
    }

    public class VideosDemotrativoHolder extends RecyclerView.ViewHolder {
        TextView txttitulovideodemo, txtdescripvideodemo, txturlvideodemo;
        VideoView videodemo;
        YouTubePlayerView youTubePlayerView;

        public VideosDemotrativoHolder(@NonNull View itemView) {
            super(itemView);
            txttitulovideodemo=itemView.findViewById(R.id.txt_titulovideodemo);
            txtdescripvideodemo=itemView.findViewById(R.id.txt_descvideodemo);
            videodemo=itemView.findViewById(R.id.VV_urlvideodemo);
            youTubePlayerView=itemView.findViewById(R.id.YP_videoplayer);
        }
    }
}
