//Creado por Duran Llamacuri Kevin - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.com.encargalo.repartidor.Entidades.pe_claseproducto_lista;
import mx.com.repartidor.R;

public class pe_adplistaproductos  extends RecyclerView.Adapter<pe_adplistaproductos.RecyclerHolder>{
    LayoutInflater inflater;
    ArrayList<pe_claseproducto_lista> model;
    Context context;

    public pe_adplistaproductos(Context context, ArrayList<pe_claseproducto_lista> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public pe_adplistaproductos.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.pe_detitemproductolista, parent,false);
        return new pe_adplistaproductos.RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull pe_adplistaproductos.RecyclerHolder holder, int position) {
        holder.pe_dettxtitemdescripcion.setText(model.get(position).getDescripcion());
        holder.pe_dettxtitemcantidad.setText(model.get(position).getCantidad());
        holder.pe_dettxtitemsubtotal.setText(model.get(position).getSubTotal());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView pe_dettxtitemdescripcion, pe_dettxtitemcantidad, pe_dettxtitemsubtotal;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            pe_dettxtitemdescripcion=itemView.findViewById(R.id.pe_dettxtitemdescripcion);
            pe_dettxtitemcantidad=itemView.findViewById(R.id.pe_dettxtitemcantidad);
            pe_dettxtitemsubtotal=itemView.findViewById(R.id.pe_dettxtitemsubtotal);
        }
    }
}
