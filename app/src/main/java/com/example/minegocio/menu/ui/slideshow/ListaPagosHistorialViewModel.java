package com.example.minegocio.menu.ui.slideshow;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.HistorialPagoDTO;
import com.example.minegocio.request.ApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPagosHistorialViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<List<HistorialPagoDTO>> mLista = new MutableLiveData<>();
    public ListaPagosHistorialViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<List<HistorialPagoDTO>> getMLista(){
        return mLista;
    }

    public void cargarLista(Bundle bundle){
        String desde = bundle.getString("fechaMin");
        String hasta = bundle.getString("fechaMax");
        Boolean tarjeta = bundle.getBoolean("tarjeta");
        Boolean transfer = bundle.getBoolean("transfer");
        Boolean efectivo = bundle.getBoolean("efectivo");

        String desdeISO = convertirAISO(desde);
        String hastaISO = convertirAISO(hasta);

        if(desdeISO == null || hastaISO == null){
            mError.setValue("Error al convertir fechas");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        Call<List<HistorialPagoDTO>> llamada = ApiClient.getApi().getHistorial("Bearer "+token,
                                                                desdeISO,hastaISO,efectivo,tarjeta,transfer);
        llamada.enqueue(new Callback<List<HistorialPagoDTO>>() {
            @Override
            public void onResponse(Call<List<HistorialPagoDTO>> call, Response<List<HistorialPagoDTO>> response) {
                if(response.isSuccessful()){
                    mLista.postValue(response.body());
                }else{
                    mError.setValue("Error al obtener pagos");
                }
            }

            @Override
            public void onFailure(Call<List<HistorialPagoDTO>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }

    private String convertirAISO(String fechaDDMMYYYY) {
        try {
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat sdfSalida = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            Date fecha = sdfEntrada.parse(fechaDDMMYYYY);
            return sdfSalida.format(fecha);
        } catch (Exception e) {
            return null;
        }
    }

}