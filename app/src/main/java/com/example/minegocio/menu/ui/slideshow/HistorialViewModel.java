package com.example.minegocio.menu.ui.slideshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HistorialViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFlagTarjeta = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFlagTransfer = new MutableLiveData<>();
    private MutableLiveData<Boolean> mFlagEfectivo = new MutableLiveData<>();
    public HistorialViewModel(@NonNull Application application) {
        super(application);
        mFlagTarjeta.setValue(false);
        mFlagTransfer.setValue(false);
        mFlagEfectivo.setValue(false);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<Boolean> getMFlagTarjeta(){
        return mFlagTarjeta;
    }
    public LiveData<Boolean> getMFlagTransfer(){
        return mFlagTransfer;
    }
    public LiveData<Boolean> getMFlagEfectivo(){
        return mFlagEfectivo;
    }

    public void setearTarjeta(){
        Boolean actual = mFlagTarjeta.getValue();
        mFlagTarjeta.setValue(actual == null ? true : !actual);
    }
    public void setearTransfer(){
        Boolean actual = mFlagTransfer.getValue();
        mFlagTransfer.setValue(actual == null ? true : !actual);
    }
    public void setearEfectivo(){
        Boolean actual = mFlagEfectivo.getValue();
        mFlagEfectivo.setValue(actual == null ? true : !actual);
    }

    public void limpiarFiltros(){
        mFlagTarjeta.setValue(false);
        mFlagEfectivo.setValue(false);
        mFlagTransfer.setValue(false);
    }

    public boolean validarCampos(String desde, String hasta){
        if(desde == null || desde.isEmpty()) {
            mError.setValue("Error: debe seleccionar la fecha mínima");
            return false;
        }

        if(hasta == null || hasta.isEmpty()) {
            mError.setValue("Error: debe seleccionar la fecha límite");
            return false;
        }

        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate fechaDesde = LocalDate.parse(desde, f);
            LocalDate fechaHasta = LocalDate.parse(hasta, f);

            if(fechaHasta.isBefore(fechaDesde)) {
                mError.setValue("Error: la fecha límite debe ser posterior a la fecha mínima");
                return false;
            }
        } catch (Exception e) {
            mError.setValue("Error: formato de fecha inválido");
            return false;
        }

        return true;
    }
}