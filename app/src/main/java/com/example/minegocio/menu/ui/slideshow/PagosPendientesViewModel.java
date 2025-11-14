package com.example.minegocio.menu.ui.slideshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.TurnoDTO;
import com.example.minegocio.request.ApiClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosPendientesViewModel extends AndroidViewModel {
    private MutableLiveData<List<TurnoDTO>> mLista = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    public PagosPendientesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<TurnoDTO>> getMLista(){
        return mLista;
    }
    public LiveData<String> getMError(){
        return mError;
    }

    public void cargarLista(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<TurnoDTO>> llamada = ApiClient.getApi().getTurnosPagoPendiente("Bearer "+token);
        llamada.enqueue(new Callback<List<TurnoDTO>>() {
            @Override
            public void onResponse(Call<List<TurnoDTO>> call, Response<List<TurnoDTO>> response) {
                if(response.isSuccessful()){
                    List<TurnoDTO> listaOrdenada = response.body();
                    Collections.sort(listaOrdenada, new Comparator<TurnoDTO>() {
                        @Override
                        public int compare(TurnoDTO t1, TurnoDTO t2) {
                            try {
                                LocalDateTime fecha1 = LocalDateTime.parse(t1.getFecha());
                                LocalDateTime fecha2 = LocalDateTime.parse(t2.getFecha());
                                return fecha1.compareTo(fecha2);
                            } catch (Exception e) {
                                return 0;
                            }
                        }
                    });
                    mLista.setValue(listaOrdenada);
                }else {
                    mError.setValue("Error al conseguir los turnos");
                }
            }

            @Override
            public void onFailure(Call<List<TurnoDTO>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }
}