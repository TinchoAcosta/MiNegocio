package com.example.minegocio.menu.ui.slideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minegocio.R;
import com.example.minegocio.models.DTOs.HistorialPagoDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ListaDelHistorialAdapter extends RecyclerView.Adapter<ListaDelHistorialAdapter.ViewHolderHistorial>{

    private List<HistorialPagoDTO> lista;
    private Context context;
    private LayoutInflater li;
    private DecimalFormat formatoPrecio = new DecimalFormat("#,##0.00");
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    public ListaDelHistorialAdapter(Context context, LayoutInflater li, List<HistorialPagoDTO> lista) {
        this.context = context;
        this.li = li;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = li.inflate(R.layout.item_historial_de_pagos,parent,false);
        return new ListaDelHistorialAdapter.ViewHolderHistorial(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistorial holder, int position) {
        if (lista.isEmpty()) {
            holder.servicio.setText("No hay pagos registrados en el intervalo de fechas seleccionadas");
            holder.fechaPago.setVisibility(View.GONE);
            holder.fechaTurno.setVisibility(View.GONE);
            holder.cliente.setVisibility(View.GONE);
            holder.monto.setVisibility(View.GONE);
            holder.metodo.setVisibility(View.GONE);
        } else {
            HistorialPagoDTO pagoActual = lista.get(position);

            if(pagoActual.getPromoDescripcion() != null){
                holder.promo.setText("Promo: "+pagoActual.getPromoDescripcion());
                holder.promo.setVisibility(View.VISIBLE);
            }
            holder.servicio.setText(pagoActual.getServicioDetalle());
            holder.cliente.setText(pagoActual.getClienteNombre()+" "+pagoActual.getClienteApellido());
            holder.metodo.setText(pagoActual.getMetodoDePago());
            String precioFormateado = formatoPrecio.format(pagoActual.getMonto());
            holder.monto.setText("$ "+precioFormateado);

            if (pagoActual.getFechaPago() != null) {
                String fechaPagoStr = formatoFecha.format(pagoActual.getFechaPago());
                holder.fechaPago.setText("Pagado: " + fechaPagoStr);
            }

            if (pagoActual.getFechaTurno() != null) {
                String fechaTurnoStr = formatoFecha.format(pagoActual.getFechaTurno());
                holder.fechaTurno.setText("Turno: " + fechaTurnoStr);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lista.isEmpty() ? 1 : lista.size();
    }

    public class ViewHolderHistorial extends RecyclerView.ViewHolder{
        TextView fechaPago, fechaTurno, cliente, monto, metodo, servicio, promo;
        public ViewHolderHistorial(@NonNull View itemView) {
            super(itemView);
            servicio = itemView.findViewById(R.id.tvServicioHistorialDePagos);
            fechaPago = itemView.findViewById(R.id.tvFechaPagoHistorialDePagos);
            fechaTurno = itemView.findViewById(R.id.tvFechaTurnoHistorialDePagos);
            cliente = itemView.findViewById(R.id.tvClienteHistorialDePagos);
            monto = itemView.findViewById(R.id.tvMontoHistorialDePagos);
            metodo = itemView.findViewById(R.id.tvMetodoDePagoHistorialDePagos);
            promo = itemView.findViewById(R.id.tvPromoDescripHistorialDePagos);
        }
    }

}
