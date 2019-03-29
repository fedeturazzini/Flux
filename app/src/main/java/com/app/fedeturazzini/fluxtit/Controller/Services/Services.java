package com.app.fedeturazzini.fluxtit.Controller.Services;

import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.Model.Pets;
import com.app.fedeturazzini.fluxtit.Model.Pets_;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("findByStatus")
    Call<ArrayList<Pets>> getPets(@Query("status") String available);
}
