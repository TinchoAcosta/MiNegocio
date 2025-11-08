package com.example.minegocio.menu.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentTurnosBinding;
import com.example.minegocio.models.DTOs.TurnoDTO;
import com.example.minegocio.models.Turno;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TurnosFragment extends Fragment {

    private TurnosViewModel mv;
    private FragmentTurnosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(TurnosViewModel.class);
        binding = FragmentTurnosBinding.inflate(inflater, container, false);
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

        mv.getMLista().observe(getViewLifecycleOwner(), new Observer<List<TurnoDTO>>() {
            @Override
            public void onChanged(List<TurnoDTO> turnos) {
                TurnosDelDiaAdapter la = new TurnosDelDiaAdapter(turnos, getContext(), getLayoutInflater(), new TurnosDelDiaAdapter.OnClickClienteListener() {
                    @Override
                    public void OnClick(int clienteId) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("clienteId",clienteId);
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.informacionClienteFragment,bundle);
                    }
                });
                GridLayoutManager glm = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                binding.rvTurnosDelDia.setLayoutManager(glm);
                binding.rvTurnosDelDia.setAdapter(la);
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