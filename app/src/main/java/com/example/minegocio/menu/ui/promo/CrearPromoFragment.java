package com.example.minegocio.menu.ui.promo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.minegocio.databinding.FragmentCrearPromoBinding;
import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CrearPromoFragment extends Fragment {

    private CrearPromoViewModel mv;
    private FragmentCrearPromoBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static CrearPromoFragment newInstance() {
        return new CrearPromoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CrearPromoViewModel.class);
        binding = FragmentCrearPromoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        abrirGaleria();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorPromoForm.setVisibility(View.VISIBLE);
                binding.tvErrorPromoForm.setText(s);
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

        mv.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivPortadaPromoForm.setImageURI(uri);
            }
        });

        binding.btnAgregarFotoPromoForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });

        mv.getMServicios().observe(getViewLifecycleOwner(), new Observer<List<ServicioPropioDTO>>() {
            @Override
            public void onChanged(List<ServicioPropioDTO> servicioPropioDTOS) {
                List<ServicioPropioDTO> listaTemporal = new ArrayList<>();
                listaTemporal.addAll(servicioPropioDTOS);
                ArrayAdapter<ServicioPropioDTO> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listaTemporal
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spServiciosPropiosPromoForm.setAdapter(adapter);
                binding.spServiciosPropiosPromoForm.setSelection(0);
            }
        });

        binding.etFechaPromoForm.setOnClickListener(new View.OnClickListener() {
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
                            binding.etFechaPromoForm.setText(fechaSeleccionada);
                        },
                        anio, mes, dia
                );
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.show();
            }
        });

        binding.btnGuardarPromoForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrorPromoForm.setVisibility(View.GONE);
                mv.crearPromo(binding.etDescripcionPromoForm.getText().toString(),
                              binding.etCondicionPromoForm.getText().toString(),
                              binding.etPrecioPromoForm.getText().toString(),
                              binding.etFechaPromoForm.getText().toString(),
                              binding.spServiciosPropiosPromoForm.getSelectedItem());
            }
        });


        Bundle args = getArguments();
        if (args != null && args.containsKey("servicio")) {
            ServicioPropioDTO servicio = (ServicioPropioDTO) args.getSerializable("servicio");
            if (servicio != null) {
                cargarServicioUnico(servicio);
                // Deshabilitar el spinner para que el usuario no pueda cambiarlo
                binding.spServiciosPropiosPromoForm.setEnabled(false);
            }
        }else{
            mv.cargarSpinner();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mv.recibirFoto(result);
            }
        });
    }

    private void cargarServicioUnico(ServicioPropioDTO servicio){
        List<ServicioPropioDTO> listaUnica = new ArrayList<>();
        listaUnica.add(servicio);

        ArrayAdapter<ServicioPropioDTO> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                listaUnica
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spServiciosPropiosPromoForm.setAdapter(adapter);
        binding.spServiciosPropiosPromoForm.setSelection(0);
    }

}