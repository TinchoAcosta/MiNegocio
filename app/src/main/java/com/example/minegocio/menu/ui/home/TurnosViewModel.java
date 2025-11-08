package com.example.minegocio.menu.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.TurnoDTO;
import com.example.minegocio.request.ApiClient;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurnosViewModel extends AndroidViewModel {
    private MutableLiveData<List<TurnoDTO>> mLista = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    public TurnosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<TurnoDTO>> getMLista(){
        return mLista;
    }
    public LiveData<String> getMError(){
        return mError;
    }

    public void cargarLista(Bundle bundle){
        String token = ApiClient.leerToken(getApplication());
        int anio = bundle.getInt("año");
        int mes = bundle.getInt("mes");
        int dia = bundle.getInt("dia");
        String fechaParam = String.format(Locale.getDefault(), "%04d-%02d-%02d", anio, mes, dia);
        Call<List<TurnoDTO>> llamada = ApiClient.getApi().getTurnosDelDia("Bearer "+token,fechaParam);
        llamada.enqueue(new Callback<List<TurnoDTO>>() {
            @Override
            public void onResponse(Call<List<TurnoDTO>> call, Response<List<TurnoDTO>> response) {
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
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