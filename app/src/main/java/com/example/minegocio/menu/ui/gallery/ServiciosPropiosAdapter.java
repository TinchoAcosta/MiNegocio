package com.example.minegocio.menu.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minegocio.R;
import com.example.minegocio.models.DTOs.ServicioPropioDTO;

import java.text.DecimalFormat;
import java.util.List;

public class ServiciosPropiosAdapter extends RecyclerView.Adapter<ServiciosPropiosAdapter.ViewHolderServicios>{

    private List<ServicioPropioDTO> lista;
    private Context context;
    private LayoutInflater li;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");
    private ServiciosPropiosAdapter.OnServicioEClickListener listenerE;
    private ServiciosPropiosAdapter.OnServicioPClickListener listenerP;

    public ServiciosPropiosAdapter(Context context, LayoutInflater li, List<ServicioPropioDTO> lista, OnServicioEClickListener listenerE, OnServicioPClickListener listenerP) {
        this.context = context;
        this.li = li;
        this.lista = lista;
        this.listenerE = listenerE;
        this.listenerP = listenerP;
    }

    @NonNull
    @Override
    public ViewHolderServicios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_servicios_propios,parent,false);
        return new ServiciosPropiosAdapter.ViewHolderServicios(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderServicios holder, int position) {
        if(lista.isEmpty()){
            holder.editar.setVisibility(View.GONE);
            holder.hacerPromo.setVisibility(View.GONE);
            holder.detalle.setText("¡Usted no posee servicios actualmente!");
        }else {
            ServicioPropioDTO servicioActual = lista.get(position);
            holder.detalle.setText(servicioActual.getDetalle());
            holder.categoria.setText(servicioActual.getCategoria()+" -");
            holder.duracion.setText(String.valueOf(servicioActual.getDuracionMinutos())+"min");
            String precioFormateado = formatoPrecio.format(servicioActual.getPrecioBase());
            holder.precio.setText("$ "+precioFormateado);

            holder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerE.OnEditarClick(servicioActual);
                }
            });
            holder.hacerPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerP.OnHacerPromoClick(servicioActual);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderServicios extends RecyclerView.ViewHolder{
        TextView detalle, categoria, precio, duracion;
        Button editar, hacerPromo;
        public ViewHolderServicios(@NonNull View itemView) {
            super(itemView);
            detalle = itemView.findViewById(R.id.tvDetalleServicio);
            categoria = itemView.findViewById(R.id.tvCategoriaServicio);
            precio = itemView.findViewById(R.id.tvPrecioBaseServicio);
            duracion = itemView.findViewById(R.id.tvDuracionServicio);
            editar = itemView.findViewById(R.id.btnEditarServicio);
            hacerPromo = itemView.findViewById(R.id.btnHacerPromo);
        }
    }

    public interface OnServicioEClickListener{
        void OnEditarClick(ServicioPropioDTO servicio);
    }
    public interface OnServicioPClickListener{
        void OnHacerPromoClick(ServicioPropioDTO servicio);
    }
}
