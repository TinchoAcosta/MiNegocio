package com.example.minegocio.menu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.minegocio.R;
import com.example.minegocio.databinding.FragmentHomeBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnHoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay today = CalendarDay.today();
                binding.materialCalendarView.clearSelection();
                binding.materialCalendarView.setCurrentDate(today);
                binding.materialCalendarView.setSelectedDate(today);
            }
        });

        binding.btnConfirmarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay fechaActual = binding.materialCalendarView.getSelectedDate();
                int anio = fechaActual.getYear();
                int mes= fechaActual.getMonth() + 1;
                int dia= fechaActual.getDay();
                Bundle bundle = new Bundle();
                bundle.putInt("año",anio);
                bundle.putInt("mes",mes);
                bundle.putInt("dia",dia);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_menu).navigate(R.id.turnosFragment,bundle);
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