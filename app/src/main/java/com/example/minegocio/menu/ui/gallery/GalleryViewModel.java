package com.example.minegocio.menu.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.example.minegocio.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryViewModel extends AndroidViewModel {
    private MutableLiveData<List<ServicioPropioDTO>> mLista = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<List<ServicioPropioDTO>> getMLista(){
        return mLista;
    }

    public void cargarLista(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<ServicioPropioDTO>> llamada = ApiClient.getApi().getServiciosPropios("Bearer "+token);
        llamada.enqueue(new Callback<List<ServicioPropioDTO>>() {
            @Override
            public void onResponse(Call<List<ServicioPropioDTO>> call, Response<List<ServicioPropioDTO>> response) {
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                }else {
                    mError.setValue("Error al obtener los servicios");
                }
            }

            @Override
            public void onFailure(Call<List<ServicioPropioDTO>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }
}