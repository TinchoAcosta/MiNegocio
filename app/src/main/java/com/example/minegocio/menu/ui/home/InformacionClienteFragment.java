package com.example.minegocio.menu.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentHomeBinding;
import com.example.minegocio.databinding.FragmentInformacionClienteBinding;
import com.example.minegocio.models.Cliente;

public class InformacionClienteFragment extends Fragment {

    private InformacionClienteViewModel mv;
    private FragmentInformacionClienteBinding binding;

    public static InformacionClienteFragment newInstance() {
        return new InformacionClienteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InformacionClienteViewModel.class);
        binding = FragmentInformacionClienteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getMError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrorCliente.setVisibility(View.VISIBLE);
                binding.tvErrorCliente.setText(s);
            }
        });

        mv.getMCliente().observe(getViewLifecycleOwner(), new Observer<Cliente>() {
            @Override
            public void onChanged(Cliente cliente) {
                binding.tvErrorCliente.setVisibility(View.GONE);

                binding.tvNombreCliente.setText(cliente.getNombre()+" "+cliente.getApellido());
                binding.tvTelefonoCliente.setText("Teléfono: "+cliente.getTelefono());
                binding.tvEmailCliente.setText("Email: "+cliente.getEmail());
                binding.tvDniCliente.setText("DNI: "+cliente.getDni());
                if(cliente.getDomicilio()!=null){
                    binding.tvDomicilioCliente.setText("Domicilio: "+cliente.getDomicilio());
                }else {
                    binding.tvDomicilioCliente.setText("DOMICILIO NO REGISTRADO");
                }
            }
        });

        mv.cargarCliente(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}