package mx.com.encargalo.repartidor.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.com.encargalo.repartidor.Entidades.sp_Entidaddesarrollador;
import mx.com.repartidor.R;


public class sp_Adapterdesarrollador extends RecyclerView.Adapter<sp_Adapterdesarrollador.DesarrolladorHolder> {
    List<sp_Entidaddesarrollador> listadesarrolladores;

    public sp_Adapterdesarrollador(List<sp_Entidaddesarrollador> listaIndicadores){
        this.listadesarrolladores = listaIndicadores;
    }

    @NonNull
    @Override

    public DesarrolladorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_itemdesarrolladores,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new DesarrolladorHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final DesarrolladorHolder holder, int position) {
        final sp_Entidaddesarrollador item = listadesarrolladores.get(position);
        holder.txt_so_07_autor.setText(String.valueOf(listadesarrolladores.get(position).getNombre()));
        holder.txt_so_07_cargo.setText(String.valueOf(listadesarrolladores.get(position).getCargo()));
        holder.txt_so_07_correo.setText(String.valueOf(listadesarrolladores.get(position).getCorreo()));

    }



    @Override
    public int getItemCount() {
        return  listadesarrolladores.size();
    }

    public class DesarrolladorHolder extends RecyclerView.ViewHolder {
        TextView txt_so_07_autor, txt_so_07_cargo, txt_so_07_correo;


        public DesarrolladorHolder(@NonNull View itemView) {
            super(itemView);
            txt_so_07_autor=itemView.findViewById(R.id.txt_so_07_autor);
            txt_so_07_cargo=itemView.findViewById(R.id.txt_so_07_cargo);
            txt_so_07_correo=itemView.findViewById(R.id.txt_so_07_correo);
        }
    }
}
