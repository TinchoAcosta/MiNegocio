package com.example.minegocio.menu.ui.slideshow;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.Pago;
import com.example.minegocio.request.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarPagoFormViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();

    private String regexMonto = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$";
    public RegistrarPagoFormViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }

    public void registrarPago(String fechaS,
                              String montoS,
                              String metodoS,
                              Bundle bundle){
        String fechaFormateada = formatearFecha(fechaS);
        String token = ApiClient.leerToken(getApplication());

        if(metodoS.equals("Selecciona el método de pago")){
            mError.setValue("Error: seleleccione un metodo de pago");
            return;
        }

        if(fechaFormateada == null){
            mError.setValue("Error: fecha inválida");
            return;
        }

        if(!montoS.matches(regexMonto)){
            mError.setValue("Error: ingrese un monto válido");
            return;
        }

        int turnoId = bundle.getInt("turnoId");

        Pago p = new Pago(0,fechaFormateada,Double.parseDouble(montoS),metodoS,turnoId);
        Call<Pago> llamada = ApiClient.getApi().crearPago("Bearer "+token,p);
        llamada.enqueue(new Callback<Pago>() {
            @Override
            public void onResponse(Call<Pago> call, Response<Pago> response) {
                if(response.isSuccessful()){
                    mExito.setValue("¡Pago registrado exitosamente!");
                }else{
                    mError.setValue("Error al registrar el pago");
                }
            }

            @Override
            public void onFailure(Call<Pago> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });

    }

    private String formatearFecha(String fecha1){
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formatoISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            Date fecha = formatoEntrada.parse(fecha1);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);

            return formatoISO.format(calendar.getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}