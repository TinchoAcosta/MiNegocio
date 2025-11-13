package com.example.minegocio.menu.ui.promo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.PromoDTO;
import com.example.minegocio.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromosViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    private MutableLiveData<List<PromoDTO>> mLista = new MutableLiveData<>();
    public PromosViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }
    public LiveData<List<PromoDTO>> getMLista(){
        return mLista;
    }

    public void cargarLista(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<PromoDTO>> llamada = ApiClient.getApi().getPromos("Bearer "+token);
        llamada.enqueue(new Callback<List<PromoDTO>>() {
            @Override
            public void onResponse(Call<List<PromoDTO>> call, Response<List<PromoDTO>> response) {
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                }else {
                    mError.setValue("Error al cargar las promociones");
                }
            }

            @Override
            public void onFailure(Call<List<PromoDTO>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }

    public void eliminarPromo(int promoId) {
        String token = ApiClient.leerToken(getApplication());
        Call<ResponseBody> llamada = ApiClient.getApi().eliminarPromo("Bearer " + token, promoId);
        llamada.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    mExito.setValue("Promoción eliminada");
                    eliminarDeListaLocal(promoId);
                }else{
                    mError.setValue("Error al eliminar promo");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }

    private void eliminarDeListaLocal(int promoId) {
        List<PromoDTO> listaActual = mLista.getValue();
        if (listaActual != null) {
            List<PromoDTO> nuevaLista = new ArrayList<>(listaActual);
            nuevaLista.removeIf(p -> p.getId() == promoId);
            mLista.postValue(nuevaLista);
        }
    }
}