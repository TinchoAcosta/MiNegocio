package com.example.minegocio.menu.ui.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentGalleryBinding;
import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryViewModel mv;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
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

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<ServicioPropioDTO>>() {
            @Override
            public void onChanged(List<ServicioPropioDTO> serviciosPropios) {
                ServiciosPropiosAdapter la = new ServiciosPropiosAdapter(getContext(), getLayoutInflater(), serviciosPropios, new ServiciosPropiosAdapter.OnServicioEClickListener() {
                    @Override
                    public void OnEditarClick(ServicioPropioDTO servicio) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("servicioId",servicio.getId());
                        bundle.putSerializable("servicio",servicio);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.formServicioFragment,bundle);
                    }
                }, new ServiciosPropiosAdapter.OnServicioPClickListener() {
                    @Override
                    public void OnHacerPromoClick(ServicioPropioDTO servicio) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("servicio",servicio);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.crearPromoFragment,bundle);
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvMisServicios.setLayoutManager(glm);
                binding.rvMisServicios.setAdapter(la);
            }
        });

        binding.fabAgregarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.formServicioFragment);
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