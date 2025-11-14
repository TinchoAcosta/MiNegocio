package com.example.minegocio.menu.ui.slideshow;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.minegocio.databinding.FragmentRegistrarPagoFormBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistrarPagoFormFragment extends Fragment {

    private RegistrarPagoFormViewModel mv;
    private FragmentRegistrarPagoFormBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(RegistrarPagoFormViewModel.class);
        binding = FragmentRegistrarPagoFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorRegistrarPagoForm.setVisibility(View.VISIBLE);
                binding.tvErrorRegistrarPagoForm.setText(s);
            }
        });

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#1976D2"))
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        binding.etFechaPagoForm.setOnClickListener(new View.OnClickListener() {
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
                            binding.etFechaPagoForm.setText(fechaSeleccionada);
                        },
                        anio, mes, dia
                );
                datePicker.show();
            }
        });

        binding.btGuardarRegistroPagoForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrorRegistrarPagoForm.setVisibility(View.GONE);
                mv.registrarPago(binding.etFechaPagoForm.getText().toString(),
                                 binding.etPrecioPagoForm.getText().toString(),
                                 binding.spMetodosDePagoRegistroPagoForm.getSelectedItem().toString(),
                                 getArguments());
            }
        });

        cargarSpinner();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void cargarSpinner(){
        List<String> listaTemporal = new ArrayList<>();
        listaTemporal.add("Selecciona el método de pago");
        listaTemporal.add("Transferencia");
        listaTemporal.add("Tarjeta");
        listaTemporal.add("Efectivo");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                listaTemporal
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spMetodosDePagoRegistroPagoForm.setAdapter(adapter);
        binding.spMetodosDePagoRegistroPagoForm.setSelection(0);
    }

}