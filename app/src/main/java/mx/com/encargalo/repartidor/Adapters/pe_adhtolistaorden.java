package mx.com.encargalo.repartidor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.com.encargalo.repartidor.Entidades.pe_claseorden_lista_hto;
import mx.com.repartidor.R;

public class pe_adhtolistaorden extends RecyclerView.Adapter<pe_adhtolistaorden.RecyclerHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    ArrayList<pe_claseorden_lista_hto> model;
    Context context;
    View.OnClickListener listener;

    public pe_adhtolistaorden(Context context, ArrayList<pe_claseorden_lista_hto> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public pe_adhtolistaorden.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.pe_htoitemlistaordenes, parent,false);
        v.setOnClickListener(this);
        return new pe_adhtolistaorden.RecyclerHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.pe_htotxtIdOrden.setText(model.get(position).getIdorden());
        holder.pe_htotxtNombres.setText(model.get(position).getApellido()+" "+model.get(position).getNombre());
        holder.pe_htotxtOrdCreada.setText(model.get(position).getFecha()+" - "+model.get(position).getHora());
        holder.pe_htotxtOrdEstado.setText(model.get(position).getEstado());
    }

    public  void setOnListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView pe_htotxtIdOrden, pe_htotxtNombres, pe_htotxtOrdCreada, pe_htotxtOrdEstado;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            pe_htotxtIdOrden = itemView.findViewById(R.id.pe_htotxtIdOrden);
            pe_htotxtNombres = itemView.findViewById(R.id.pe_htotxtNombres);
            pe_htotxtOrdCreada = itemView.findViewById(R.id.pe_htotxtOrdCreada);
            pe_htotxtOrdEstado = itemView.findViewById(R.id.pe_htotxtOrdEstado);
        }
    }
}

