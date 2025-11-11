package com.example.minegocio.menu.ui.gallery;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.minegocio.databinding.FragmentFormServicioBinding;
import com.example.minegocio.models.ServicioBase;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormServicioFragment extends Fragment {

    private FormServicioViewModel mv;
    private FragmentFormServicioBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(FormServicioViewModel.class);
        binding = FragmentFormServicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle args = getArguments();
        boolean esEdicion = args != null;


        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorServicioForm.setVisibility(View.VISIBLE);
                binding.tvErrorServicioForm.setText(s);
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

        mv.getMCategorias().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                List<String> listaTemporal = new ArrayList<>();
                if(!esEdicion){
                    listaTemporal.add("Selecciona una categoria");
                }
                listaTemporal.addAll(strings);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listaTemporal
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spServicioBase.setAdapter(adapter);
                binding.spServicioBase.setSelection(0);
            }
        });

        mv.getMDetalles().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                List<String> listaTemporal = new ArrayList<>();
                if(!esEdicion){
                    listaTemporal.add("Selecciona un servicio");
                }
                listaTemporal.addAll(strings);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listaTemporal
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spDetallesServicio.setAdapter(adapter);
                binding.spDetallesServicio.setSelection(0);
            }
        });

        binding.spServicioBase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    binding.spDetallesServicio.setEnabled(false);
                    binding.spDetallesServicio.setAlpha(0.6f);
                    binding.spDetallesServicio.setSelection(0);
                }else {
                    binding.spDetallesServicio.setEnabled(true);
                    binding.spDetallesServicio.setAlpha(1f);
                    mv.cargarSpinnerDetalles(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.btnGuardarServicioPropio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrorServicioForm.setVisibility(View.GONE);
                mv.guardarEditar(getArguments(),
                        binding.etDuracionMinutos.getText().toString(),
                        binding.etPrecioServicioPropio.getText().toString(),
                        binding.spServicioBase.getSelectedItem().toString(),
                        binding.spDetallesServicio.getSelectedItem().toString());
            }
        });

        if (esEdicion) {
            mv.inicializarEdicion(getArguments());
            binding.spServicioBase.setEnabled(false);
            binding.spServicioBase.setAlpha(0.6f);
            binding.spDetallesServicio.setEnabled(false);
            binding.spDetallesServicio.setAlpha(0.6f);
        } else {
            mv.cargarSpinner();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}