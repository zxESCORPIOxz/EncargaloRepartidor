package mx.com.encargalo.repartidor.Adapters;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.com.encargalo.repartidor.Entidades.sp_EntidadPreguntasFrecuentes;
import mx.com.repartidor.R;


public class sp_AdapterPreguntasFrecuentes extends RecyclerView.Adapter<sp_AdapterPreguntasFrecuentes.PreguntasFrecuentesHolder> {

    List<sp_EntidadPreguntasFrecuentes> listapregfrec;

    public sp_AdapterPreguntasFrecuentes(List<sp_EntidadPreguntasFrecuentes> listaIndicadores){
        this.listapregfrec = listaIndicadores;
    }
    @NonNull
    @Override
    public PreguntasFrecuentesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_itempreguntasfrecuentes,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new PreguntasFrecuentesHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final PreguntasFrecuentesHolder holder, int position) {
        final sp_EntidadPreguntasFrecuentes item = listapregfrec.get(position);

        holder.txt_so_02_pregunta.setText(String.valueOf(listapregfrec.get(position).getPregunta()));
//        holder.txt_so_02_respuesta.setText(String.valueOf(listapregfrec.get(position).getRespuesta()));
//        holder.txt_so_02_urlvideo.setText(String.valueOf(listapregfrec.get(position).getURL_video()));
//        holder.txt_so_02_urlimg.setText(String.valueOf(listapregfrec.get(position).getURL_img()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("pregunta", item.getPregunta());
                bundle.putString("respuesta", item.getRespuesta());
                bundle.putString("URL_vid", item.getURL_video());
                bundle.putString("URL_img", item.getURL_img());

                Navigation.findNavController(v).navigate(R.id.nav_detallepregunta, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {

        return  listapregfrec.size();
    }

    public class PreguntasFrecuentesHolder extends RecyclerView.ViewHolder {
        TextView txt_so_02_pregunta, txt_so_02_respuesta, txt_so_02_urlvideo,txt_so_02_urlimg;

        public PreguntasFrecuentesHolder(@NonNull View itemView) {
            super(itemView);
            txt_so_02_pregunta=itemView.findViewById(R.id.txt_so_02_pregunta);
//            txt_so_02_respuesta=itemView.findViewById(R.id.txt_so_02_respuesta);
//            txt_so_02_urlvideo=itemView.findViewById(R.id.txt_so_02_urlvideo);
//            txt_so_02_urlimg=itemView.findViewById(R.id.txt_so_02_urlimg);
        }
    }
}