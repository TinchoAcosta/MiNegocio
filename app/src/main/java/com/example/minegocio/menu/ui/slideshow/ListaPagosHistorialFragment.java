package com.example.minegocio.menu.ui.slideshow;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minegocio.databinding.FragmentListaPagosHistorialBinding;
import com.example.minegocio.models.DTOs.HistorialPagoDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListaPagosHistorialFragment extends Fragment {

    private ListaPagosHistorialViewModel mv;
    private FragmentListaPagosHistorialBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ListaPagosHistorialViewModel.class);
        binding = FragmentListaPagosHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#E57373"))
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<HistorialPagoDTO>>() {
            @Override
            public void onChanged(List<HistorialPagoDTO> historialPagoDTOS) {
                ListaDelHistorialAdapter la = new ListaDelHistorialAdapter(getContext(),getLayoutInflater(),historialPagoDTOS);
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvHistorialDePagos.setLayoutManager(glm);
                binding.rvHistorialDePagos.setAdapter(la);
            }
        });

        mv.cargarLista(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}