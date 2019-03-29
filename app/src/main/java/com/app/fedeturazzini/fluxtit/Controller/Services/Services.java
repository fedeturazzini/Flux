package com.app.fedeturazzini.fluxtit.Controller.Services;

import com.app.fedeturazzini.fluxtit.Model.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("findByStatus")
    Call<List<Pet>> getPets(@Query("status") String available);
}
