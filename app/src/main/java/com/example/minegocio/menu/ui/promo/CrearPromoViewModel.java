package com.example.minegocio.menu.ui.promo;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.models.DTOs.PromoDTO;
import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.example.minegocio.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearPromoViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<String> mExito = new MutableLiveData<>();
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private MutableLiveData<List<ServicioPropioDTO>> mServicios = new MutableLiveData<>();

    private String regexMonto = "^(?:0|[1-9]\\d*)(?:\\.\\d{1,2})?$";

    public CrearPromoViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMError(){
        return mError;
    }
    public LiveData<String> getMExito(){
        return mExito;
    }
    public LiveData<List<ServicioPropioDTO>> getMServicios(){
        return mServicios;
    }
    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public void cargarSpinner(){
        String token = ApiClient.leerToken(getApplication());
        Call<List<ServicioPropioDTO>> llamada = ApiClient.getApi().getServiciosPropios("Bearer "+token);
        llamada.enqueue(new Callback<List<ServicioPropioDTO>>() {
            @Override
            public void onResponse(Call<List<ServicioPropioDTO>> call, Response<List<ServicioPropioDTO>> response) {
                if(response.isSuccessful()){
                    mServicios.postValue(response.body());
                }else{
                    mError.setValue("Error al obtener los servicios propios");
                }
            }

            @Override
            public void onFailure(Call<List<ServicioPropioDTO>> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });
    }

    public void crearPromo(String descripS,
                           String condicionS,
                           String precioS,
                           String fechaS,
                           Object servicioO){
        String fechaFormateada;
        ServicioPropioDTO servicio = (ServicioPropioDTO) servicioO;

        if(descripS.isBlank()){
            mError.setValue("Error: el campo de descripción es requerido");
            return;
        }
        if(condicionS.isBlank() && precioS.isBlank()){
            mError.setValue("Error: la promo debe tener un precio o una condición a cumplir");
            return;
        }
        if(!precioS.isBlank() && !precioS.matches(regexMonto)){
            mError.setValue("Error: ingrese un precio válido");
            return;
        }
        if(formatearFecha(fechaS)!=null){
            fechaFormateada = formatearFecha(fechaS);
        }else {
            mError.setValue("Error: fecha inválida");
            return;
        }

        PromoDTO promo = new PromoDTO();
        promo.setDescripcion(descripS);
        promo.setCondicion(condicionS.isBlank() ? null : condicionS);
        if (!precioS.isBlank()) {
            promo.setPrecioNuevo(Double.parseDouble(precioS));
        } else {
            promo.setPrecioNuevo(null);
        }
        promo.setFechaFin(fechaFormateada);
        promo.setServicioPropio(servicio);

        String promoJson = new Gson().toJson(promo);
        RequestBody promoBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), promoJson);

        MultipartBody.Part imagenPart = null;
        byte[] imagen = transformarImagen();
        if (imagen != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
        }


        String token = ApiClient.leerToken(getApplication());
        Call<PromoDTO> llamada = ApiClient.getApi().cargarPromo("Bearer "+token,imagenPart,promoBody);
        llamada.enqueue(new Callback<PromoDTO>() {
            @Override
            public void onResponse(Call<PromoDTO> call, Response<PromoDTO> response) {
                if(response.isSuccessful()){
                    mExito.setValue("¡Promoción creada exitosamente!");
                }else{
                    mError.setValue("Error al crear la promo");
                }
            }

            @Override
            public void onFailure(Call<PromoDTO> call, Throwable t) {
                mError.setValue("Error del servidor");
            }
        });

    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();
            if (uri == null) {
                return null;
            }

            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void recibirFoto(ActivityResult result){
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            mUri.setValue(uri);
        }
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