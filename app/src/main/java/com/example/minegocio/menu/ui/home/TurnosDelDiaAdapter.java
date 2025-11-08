package com.example.minegocio.menu.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minegocio.R;
import com.example.minegocio.models.DTOs.TurnoDTO;
import com.example.minegocio.models.Turno;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TurnosDelDiaAdapter extends RecyclerView.Adapter<TurnosDelDiaAdapter.ViewHolderTurnos>{
    private List<TurnoDTO> lista;
    private Context context;
    private LayoutInflater li;
    private TurnosDelDiaAdapter.OnClickClienteListener listener;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");

    public TurnosDelDiaAdapter(List<TurnoDTO> lista, Context context, LayoutInflater li, OnClickClienteListener listener){
        this.lista = lista;
        this.context = context;
        this.li = li;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderTurnos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_turnos_del_dia,parent,false);
        return new TurnosDelDiaAdapter.ViewHolderTurnos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTurnos holder, int position) {
        if (lista.isEmpty()) {
            holder.servicio.setText("No hay turnos registrados en el dia seleccionado");
        } else {
            TurnoDTO turnoActual = lista.get(position);
            holder.servicio.setText(turnoActual.getServicioNombre() + " - " + turnoActual.getCategoria());

            holder.cliente.setText(turnoActual.getClienteNombre());
            holder.cliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(turnoActual.getClienteId());
                }
            });

            String precioFormateado = formatoPrecio.format(turnoActual.getPrecioBase());
            holder.precio.setText("$ "+precioFormateado);
            holder.descrip.setText(turnoActual.getDescripcion());
            if(turnoActual.getEstado().equalsIgnoreCase("pendiente")){
                holder.estado.setTextColor(Color.parseColor("#FFD54F"));
            }else if(turnoActual.getEstado().equalsIgnoreCase("cancelado")){
                holder.estado.setTextColor(Color.parseColor("#E57373"));
            }
            holder.estado.setText(turnoActual.getEstado().toUpperCase());

            if(!turnoActual.getPromoDescripcion().equalsIgnoreCase("SIN PROMO")){
                holder.promo.setVisibility(View.VISIBLE);
                holder.promo.setText(turnoActual.getPromoDescripcion());
            }
            if(turnoActual.getPrecioPromo()!=0){
                holder.precioPromo.setVisibility(View.VISIBLE);
                String precioPFormateado = formatoPrecio.format(turnoActual.getPrecioPromo());
                holder.precioPromo.setText("$ "+precioPFormateado);
            }

            String fechaString = turnoActual.getFecha();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaString);
            String soloFecha = fechaHora.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String soloHora = fechaHora.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            holder.fecha.setText(soloFecha);
            holder.hora.setText(soloHora);
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderTurnos extends RecyclerView.ViewHolder{
        TextView servicio, cliente, precio, descrip, estado, promo, precioPromo, fecha, hora;
        public ViewHolderTurnos(@NonNull View itemView) {
            super(itemView);
            servicio = itemView.findViewById(R.id.tvServicioBase);
            cliente = itemView.findViewById(R.id.tvCliente);
            precio = itemView.findViewById(R.id.tvPrecioBase);
            descrip = itemView.findViewById(R.id.tvDescripcion);
            estado = itemView.findViewById(R.id.tvEstado);
            promo = itemView.findViewById(R.id.tvPromo);
            precioPromo = itemView.findViewById(R.id.tvPrecioPromo);
            fecha = itemView.findViewById(R.id.tvFecha);
            hora = itemView.findViewById(R.id.tvHora);
        }
    }

    public interface OnClickClienteListener{
        void OnClick(int clienteId);
    }
}
