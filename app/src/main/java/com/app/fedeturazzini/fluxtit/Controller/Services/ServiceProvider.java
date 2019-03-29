package com.app.fedeturazzini.fluxtit.Controller.Services;

import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.Model.Pets;
import com.app.fedeturazzini.fluxtit.Model.Pets_;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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

        if (retrofit == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .build();
        }
        return retrofit;
    }

    /** GET RETROFIT REQUEST **/
    public void getPets (boolean available) {
        if (available) {
            availableFlag = "available";
        }
        Services servicePet = getRetrofit().create(Services.class);
        Call <ArrayList<Pets>> articlesCall = servicePet.getPets(availableFlag);
        articlesCall.enqueue(new Callback<ArrayList<Pets>>() {
            @Override
            public void onResponse(Call<ArrayList<Pets>> call, Response<ArrayList<Pets>> response) {
                if (response.isSuccessful()) {
                    response.body();
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pets>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
