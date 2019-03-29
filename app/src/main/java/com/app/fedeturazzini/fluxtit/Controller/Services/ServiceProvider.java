package com.app.fedeturazzini.fluxtit.Controller.Services;

import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public void getPets (boolean available) {
        if (available) {
            availableFlag = "available";
        }
        Services servicePet = getRetrofit().create(Services.class);
        Call <List<Pet>> petCall = servicePet.getPets(availableFlag);
        petCall.enqueue(new Callback <List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful()) {
//                    ArrayList<List<Pet>> listPet = response.body();
                } else {
                    response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
