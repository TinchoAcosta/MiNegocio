package com.example.minegocio;

import android.app.Application;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.minegocio.menu.MenuActivity;
import com.example.minegocio.request.ApiClient;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMError(){
        return mError;
    }

    public void login(Editable emailE, Editable claveE){
        ApiClient.NegocioService api = ApiClient.getApi();
        String mail = emailE.toString();
        String clave = claveE.toString();
        if(mail.isEmpty()){
            mError.setValue("Error: el campo de correo es obligatorio");
            return;
        }
        if(clave.isEmpty()){
            mError.setValue("Error: el campo de contraseña es obligatorio");
            return;
        }
        if(!mail.matches(regexEmail)){
            mError.setValue("Error: el correo ingresado no es válido");
            return;
        }
        Call<String> llamada = api.login(mail, clave);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token= response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }
                else
                    mError.postValue("Error: el correo y/o contraseña son incorrectos");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mError.postValue("Error de Servidor");
            }
        });
    }
}
