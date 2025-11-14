package com.example.minegocio.menu.ui.slideshow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minegocio.R;
import com.example.minegocio.models.DTOs.TurnoDTO;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagosPendientesAdapter extends RecyclerView.Adapter<PagosPendientesAdapter.ViewHolderTurnosPagosPendientes>{
    private List<TurnoDTO> lista;
    private Context context;
    private LayoutInflater li;
    private PagosPendientesAdapter.OnClickClienteListener listener;
    private PagosPendientesAdapter.OnClickRegistroListener listenerR;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");

    public PagosPendientesAdapter(Context context, LayoutInflater li, List<TurnoDTO> lista, OnClickClienteListener listener, OnClickRegistroListener listenerR) {
        this.context = context;
        this.li = li;
        this.lista = lista;
        this.listener = listener;
        this.listenerR = listenerR;
    }

    @NonNull
    @Override
    public ViewHolderTurnosPagosPendientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_pagos_pendientes,parent,false);
        return new PagosPendientesAdapter.ViewHolderTurnosPagosPendientes(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTurnosPagosPendientes holder, int position) {
        if (lista.isEmpty()) {
            holder.servicio.setText("No hay turnos registrados en el dia seleccionado");
            holder.registrarPago.setVisibility(View.GONE);
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
                holder.precio.setPaintFlags(holder.precio.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.precio.setTextColor(Color.parseColor("#B0BEC5"));
            }

            String fechaString = turnoActual.getFecha();
            LocalDateTime fechaHora = LocalDateTime.parse(fechaString);
            String soloFecha = fechaHora.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String soloHora = fechaHora.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            holder.fecha.setText(soloFecha);
            holder.hora.setText(soloHora);

            holder.registrarPago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerR.OnRegistrarPagoClick(turnoActual.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderTurnosPagosPendientes extends RecyclerView.ViewHolder{
        TextView servicio, cliente, precio, descrip, estado, promo, precioPromo, fecha, hora;
        Button registrarPago;
        public ViewHolderTurnosPagosPendientes(@NonNull View itemView) {
            super(itemView);
            servicio = itemView.findViewById(R.id.tvServicioBasePagosPendientes);
            cliente = itemView.findViewById(R.id.tvClientePagosPendientes);
            precio = itemView.findViewById(R.id.tvPrecioBaseTurnoPagosPendientes);
            descrip = itemView.findViewById(R.id.tvDescripcionPagosPendientes);
            estado = itemView.findViewById(R.id.tvEstadoPagosPendientes);
            promo = itemView.findViewById(R.id.tvPromoPagosPendientes);
            precioPromo = itemView.findViewById(R.id.tvPrecioPromoPagosPendientes);
            fecha = itemView.findViewById(R.id.tvFechaPagosPendientes);
            hora = itemView.findViewById(R.id.tvHoraPagosPendientes);
            registrarPago = itemView.findViewById(R.id.btRegistrarPago);
        }
    }

    public interface OnClickClienteListener{
        void OnClick(int clienteId);
    }

    public interface OnClickRegistroListener{
        void OnRegistrarPagoClick(int id);
    }
}
