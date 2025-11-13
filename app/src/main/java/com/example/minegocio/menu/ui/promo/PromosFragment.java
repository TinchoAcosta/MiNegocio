package com.example.minegocio.menu.ui.promo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentPromosBinding;
import com.example.minegocio.models.DTOs.PromoDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PromosFragment extends Fragment {

    private PromosViewModel mv;
    private FragmentPromosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PromosViewModel.class);
        binding = FragmentPromosBinding.inflate(inflater, container, false);
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

        mv.getMExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#1976D2"))
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<PromoDTO>>() {
            @Override
            public void onChanged(List<PromoDTO> promoDTOS) {
                PromosAdapter la = new PromosAdapter(getContext(), promoDTOS, getLayoutInflater(), new PromosAdapter.OnPromoClickListener() {
                    @Override
                    public void onVerClick(PromoDTO promo) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Confirmar eliminación")
                                .setMessage("¿Seguro que desea eliminar esta promoción?")
                                .setPositiveButton("Eliminar", (dialog, which) -> {
                                    mv.eliminarPromo(promo.getId());
                                })
                                .setNegativeButton("Cancelar", (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .show();
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvPromos.setLayoutManager(glm);
                binding.rvPromos.setAdapter(la);
            }
        });

        binding.fabAgregarPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.crearPromoFragment);
            }
        });

        mv.cargarLista();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}