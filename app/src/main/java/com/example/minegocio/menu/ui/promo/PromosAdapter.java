package com.example.minegocio.menu.ui.promo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minegocio.R;
import com.example.minegocio.models.DTOs.PromoDTO;
import com.example.minegocio.request.ApiClient;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PromosAdapter extends RecyclerView.Adapter<PromosAdapter.ViewHolderPromo>{
    private List<PromoDTO> lista;
    private Context context;
    private LayoutInflater li;
    private OnPromoClickListener listener;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");

    public PromosAdapter(Context context, List<PromoDTO> lista, LayoutInflater li, OnPromoClickListener listener) {
        this.context = context;
        this.lista = lista;
        this.li = li;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderPromo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_promos,parent,false);
        return new PromosAdapter.ViewHolderPromo(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPromo holder, int position) {
        if(lista.isEmpty()){
            holder.eliminar.setVisibility(View.GONE);
            holder.editar.setVisibility(View.GONE);
            holder.detallePromo.setText("No existen promos activas");
        }else{
            PromoDTO promoActual = lista.get(position);
            holder.detallePromo.setText(promoActual.getDescripcion());
            holder.detalleServicio.setText(promoActual.getServicioPropio().getDetalle()+" -");
            holder.categoria.setText(promoActual.getServicioPropio().getCategoria());
            holder.duracion.setText(String.valueOf(promoActual.getServicioPropio().getDuracionMinutos())+"min");

            SimpleDateFormat formatoIso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat formatoDeseado = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            try {
                Date fecha = formatoIso.parse(promoActual.getFechaFin());
                holder.fecha.setText(formatoDeseado.format(fecha));
            } catch (ParseException e) {
                holder.fecha.setText(promoActual.getFechaFin());
            }

            if(promoActual.getCondicion()!=null){
                holder.condicion.setText(promoActual.getCondicion());
            }else{
                holder.condicion.setVisibility(View.GONE);
            }
            if(promoActual.getImagen()!=null){
                String imageUrl = promoActual.getImagen().replace("\\", "/");
                String fullUrl = ApiClient.URLBASE + imageUrl;
                Glide.with(context)
                        .load(fullUrl)
                        .error(R.drawable.logo)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(holder.imagen);
            }
            if(promoActual.getPrecioNuevo()!=null && promoActual.getPrecioNuevo()!=0){
                String preciof = formatoPrecio.format(promoActual.getPrecioNuevo());
                holder.precio.setText("$ "+preciof);
            }else{
                holder.precio.setVisibility(View.GONE);
            }

            holder.eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onVerClick(promoActual);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderPromo extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView detallePromo, detalleServicio, categoria, duracion, precio, condicion, fecha;
        Button editar, eliminar;
        public ViewHolderPromo(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.ivImagenPromo);
            detallePromo = itemView.findViewById(R.id.tvDetallePromo);
            detalleServicio = itemView.findViewById(R.id.tvDetalleServicioPropioPromo);
            categoria = itemView.findViewById(R.id.tvCategoriaPromo);
            duracion = itemView.findViewById(R.id.tvDuracionPromo);
            precio = itemView.findViewById(R.id.tvPrecioNuevoPromo);
            condicion = itemView.findViewById(R.id.tvCondicionPromo);
            fecha = itemView.findViewById(R.id.tvFechaFinPromo);
            editar = itemView.findViewById(R.id.btnEditarPromo);
            eliminar = itemView.findViewById(R.id.btnEliminarPromo);
        }
    }

    public interface OnPromoClickListener {
        void onVerClick(PromoDTO promo);
    }
}
