package com.example.minegocio.menu.ui.slideshow;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentHistorialBinding;

import java.util.Calendar;
import java.util.Locale;

public class HistorialFragment extends Fragment {

    private HistorialViewModel mv;
    private FragmentHistorialBinding binding;
    private boolean camposValidos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HistorialViewModel.class);
        binding = FragmentHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorHistorial.setVisibility(View.VISIBLE);
                binding.tvErrorHistorial.setText(s);
            }
        });

        mv.getMFlagTarjeta().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btTarjetaHistorial.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor(
                                aBoolean ? "#0D47A1" : "#1976D2"
                        ))
                );
            }
        });

        mv.getMFlagEfectivo().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btEfectivoHistorial.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor(
                                aBoolean ? "#0D47A1" : "#1976D2"
                        ))
                );
            }
        });

        mv.getMFlagTransfer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btTransferHistorial.setBackgroundTintList(
                        ColorStateList.valueOf(Color.parseColor(
                                aBoolean ? "#0D47A1" : "#1976D2"
                        ))
                );
            }
        });

        binding.btTransferHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.setearTransfer();
            }
        });

        binding.btEfectivoHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.setearEfectivo();
            }
        });

        binding.btTarjetaHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.setearTarjeta();
            }
        });

        binding.btClearFiltrosHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.limpiarFiltros();
            }
        });

        binding.etFechaMinimaHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int anio = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(),
                        (view, year, month, dayOfMonth) -> {
                            String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                            binding.etFechaMinimaHistorial.setText(fechaSeleccionada);
                        },
                        anio, mes, dia
                );
                datePicker.show();
            }
        });

        binding.etFechaLimiteHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int anio = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(),
                        (view, year, month, dayOfMonth) -> {
                            String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                            binding.etFechaLimiteHistorial.setText(fechaSeleccionada);
                        },
                        anio, mes, dia
                );
                datePicker.show();
            }
        });

        binding.btBuscarHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrorHistorial.setVisibility(View.GONE);
                camposValidos = mv.validarCampos(binding.etFechaMinimaHistorial.getText().toString(),
                                 binding.etFechaLimiteHistorial.getText().toString());

                if(camposValidos){
                    Bundle bundle = new Bundle();
                    bundle.putString("fechaMin", binding.etFechaMinimaHistorial.getText().toString());
                    bundle.putString("fechaMax", binding.etFechaLimiteHistorial.getText().toString());

                    bundle.putBoolean("tarjeta", mv.getMFlagTarjeta().getValue());
                    bundle.putBoolean("efectivo", mv.getMFlagEfectivo().getValue());
                    bundle.putBoolean("transfer", mv.getMFlagTransfer().getValue());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.listaPagosHistorialFragment, bundle);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}