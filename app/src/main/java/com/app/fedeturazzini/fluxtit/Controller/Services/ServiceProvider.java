package com.app.fedeturazzini.fluxtit.Controller.Services;

import android.content.Context;
import android.widget.Toast;

import com.app.fedeturazzini.fluxtit.Controller.Events.PetByIdEvent;
import com.app.fedeturazzini.fluxtit.Controller.Events.PetEvents;
import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet/";
    private Retrofit retrofit;

    String availableFlag = "";

    /** URL RETROFIT **/
    public Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        if (retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .build();
        }
        return retrofit;
    }

    /** GET RETROFIT REQUEST **/
    public void getPets (boolean available, final Context context) {

        if (available) { // Se hardocodeo la logica solo para que funcione con available y pending. En la documentacion esta el status SOLD
            availableFlag = "available";
        } else {
            availableFlag = "pending";
        }

        Service servicePet = getRetrofit().create(Service.class);
        Call <List<Pet>> petCall = servicePet.getPets(availableFlag);
        petCall.enqueue(new Callback <List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful()) {
                    List<Pet> pets = response.body();
                    EventBus.getDefault().post(new PetEvents(pets));
                    Toast.makeText(context, "Exito", Toast.LENGTH_LONG).show();
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Fallo", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPetId (String id, final Context context) {

        Service servicePet = getRetrofit().create(Service.class);
        Call <Pet> petCall = servicePet.getPetById(id);
        petCall.enqueue(new Callback <Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Pet pet = response.body();
                    EventBus.getDefault().post(new PetByIdEvent(pet));
                    Toast.makeText(context, "Exito", Toast.LENGTH_LONG).show();
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Fallo", Toast.LENGTH_LONG).show();
            }
        });
    }



}
