package com.example.minegocio.menu.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LogoutViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mLogoutEvent = new MutableLiveData<>();

    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getMLogoutEvent(){
        return mLogoutEvent;
    }

    public void cerrarSesion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
        mLogoutEvent.setValue(true);
    }
}