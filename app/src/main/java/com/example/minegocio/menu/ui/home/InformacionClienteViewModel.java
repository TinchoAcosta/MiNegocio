package com.example.minegocio.menu.ui.home;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.Cliente;
import com.example.minegocio.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionClienteViewModel extends AndroidViewModel {
    private MutableLiveData<Cliente> mCLiente = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    public InformacionClienteViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Cliente> getMCliente(){
        return mCLiente;
    }
    public LiveData<String> getMError(){
        return mError;
    }

    public void cargarCliente(Bundle bundle){
        int clienteId = bundle.getInt("clienteId");
        String token = ApiClient.leerToken(getApplication());
        Call<Cliente> llamada = ApiClient.getApi().getClientePorId("Bearer "+token,clienteId);
        llamada.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()){
                    mCLiente.postValue(response.body());
                }else {
                    mError.setValue("¡Error al cargar los datos del cliente!");
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }
}