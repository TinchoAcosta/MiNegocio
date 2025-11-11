package com.example.minegocio.menu.ui.gallery;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.example.minegocio.models.ServicioBase;
import com.example.minegocio.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormServicioViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> mCategorias = new MutableLiveData<>();

    private MutableLiveData<List<String>> mDetalles = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    private List<String> HCategorias = new ArrayList<>();

    private List<String> HDetalles = new ArrayList<>();
    private List<ServicioBase> servicioBases = new ArrayList<>();
    private String regexEnteroPositivo = "^[1-9]\\d*$";
    private String regexMonto = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$";

    public FormServicioViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<String>> getMCategorias(){
        return mCategorias;
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<List<String>> getMDetalles(){
        return mDetalles;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }

    public void cargarSpinner(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<ServicioBase>> llamada = ApiClient.getApi().getServiciosBase("Bearer "+token);
        llamada.enqueue(new Callback<List<ServicioBase>>() {
            @Override
            public void onResponse(Call<List<ServicioBase>> call, Response<List<ServicioBase>> response) {
                if(response.isSuccessful()){
                    HCategorias.clear();
                    HDetalles.clear();
                    servicioBases = response.body();
                    for (ServicioBase s:servicioBases) {
                        if (!HCategorias.contains(s.getCategoria())){
                            HCategorias.add(s.getCategoria());
                        }
                    }
                    HDetalles.add("Selecciona un servicio");
                    mDetalles.setValue(HDetalles);
                    mCategorias.setValue(HCategorias);
                }else {
                    mError.setValue("Error al cargar los servicios");
                }
            }

            @Override
            public void onFailure(Call<List<ServicioBase>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }

    public void cargarSpinnerDetalles(String categoria){
        HDetalles.clear();
        for (ServicioBase s:servicioBases) {
            if(s.getCategoria().equalsIgnoreCase(categoria)){
                HDetalles.add(s.getDetalle());
            }
        }
        mDetalles.setValue(HDetalles);
    }

    public void inicializarEdicion(Bundle bundle){
        HDetalles.clear();
        HCategorias.clear();
        ServicioPropioDTO servicio = (ServicioPropioDTO) bundle.getSerializable("servicio");
        HDetalles.add(servicio.getDetalle());
        HCategorias.add(servicio.getCategoria());
        mDetalles.setValue(HDetalles);
        mCategorias.setValue(HCategorias);
    }

    public void guardarEditar(Bundle bundle,
                              String duracionS,
                              String precioS,
                              String categoriaS,
                              String detalleS){

        if(categoriaS.equalsIgnoreCase("Selecciona una categoria")){
            mError.setValue("Error: debe seleccionar una categoría");
            return;
        }
        if(detalleS.equalsIgnoreCase("Selecciona un servicio")){
            mError.setValue("Error: debe seleccionar un servicio");
            return;
        }

        if(!precioS.matches(regexMonto)){
            mError.setValue("Error: debe ingresar un precio válido");
            return;
        }
        if(!duracionS.matches(regexEnteroPositivo)){
            mError.setValue("Error: debe ingresar una duración válida (cantidad de minutos)");
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        ServicioPropioDTO spd = new ServicioPropioDTO();
        spd.setCategoria(categoriaS);
        spd.setDetalle(detalleS);
        spd.setPrecioBase(Double.parseDouble(precioS));
        spd.setDuracionMinutos(Integer.parseInt(duracionS));

        if(bundle != null){
            int idServicioAEditar = bundle.getInt("servicioId");
            spd.setId(idServicioAEditar);
            Call<ServicioPropioDTO> llamada = ApiClient.getApi().editarServicio("Bearer "+token,spd);
            llamada.enqueue(new Callback<ServicioPropioDTO>() {
                @Override
                public void onResponse(Call<ServicioPropioDTO> call, Response<ServicioPropioDTO> response) {
                    if(response.isSuccessful()){
                        mExito.setValue("¡Servicio modificado exitosamente!");
                    }else {
                        mError.setValue("Error al editar el servicio");
                    }
                }

                @Override
                public void onFailure(Call<ServicioPropioDTO> call, Throwable t) {
                    mError.setValue("Error del servidor");
                }
            });
        }else{
            Call<ServicioPropioDTO> llamada = ApiClient.getApi().crearServicio("Bearer "+token,spd);
            llamada.enqueue(new Callback<ServicioPropioDTO>() {
                @Override
                public void onResponse(Call<ServicioPropioDTO> call, Response<ServicioPropioDTO> response) {
                    if(response.isSuccessful()){
                        mExito.setValue("¡Servicio creado exitosamente!");
                    }else {
                        mError.setValue("Error al crear el servicio, recuerde que no puede tener servicios repetidos");
                    }
                }

                @Override
                public void onFailure(Call<ServicioPropioDTO> call, Throwable t) {
                    mError.setValue("Error del servidor");
                }
            });
        }
    }
}