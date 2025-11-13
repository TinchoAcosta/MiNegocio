package com.example.minegocio.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.minegocio.models.Cliente;
import com.example.minegocio.models.DTOs.PromoDTO;
import com.example.minegocio.models.DTOs.ServicioPropioDTO;
import com.example.minegocio.models.DTOs.TurnoDTO;
import com.example.minegocio.models.ServicioBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class ApiClient {
    public static final String URLBASE= "http://{ip}:5035/";

    public static NegocioService getApi(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(NegocioService.class);

    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public interface NegocioService{

        @FormUrlEncoded
        @POST("Usuario/login")
        Call<String> login(@Field("email") String u, @Field("clave") String c);

        @GET("Turno/turnosDelDia")
        Call<List<TurnoDTO>> getTurnosDelDia(@Header("Authorization") String token, @Query("fecha") String fecha);

        @GET("Cliente")
        Call<Cliente> getClientePorId(@Header("Authorization") String token, @Query("id") int id);

        @GET("Servicio")
        Call<List<ServicioPropioDTO>> getServiciosPropios(@Header("Authorization") String token);

        @GET("Servicio/serviciosBase")
        Call<List<ServicioBase>> getServiciosBase(@Header("Authorization") String token);

        @POST("Servicio/crear")
        Call<ServicioPropioDTO> crearServicio(@Header("Authorization") String token, @Body ServicioPropioDTO dto);

        @PUT("Servicio/editar")
        Call<ServicioPropioDTO> editarServicio(@Header("Authorization") String token, @Body ServicioPropioDTO dto);

        @GET("Promo")
        Call<List<PromoDTO>> getPromos(@Header("Authorization") String token);

        @Multipart
        @POST("Promo/crear")
        Call<PromoDTO> cargarPromo(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("promo") RequestBody promo);

        @PUT("Promo/eliminar")
        Call<ResponseBody> eliminarPromo(@Header("Authorization") String token,
                                         @Body int id);
    }
}
