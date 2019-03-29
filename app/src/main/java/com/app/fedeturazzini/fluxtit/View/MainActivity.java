package com.app.fedeturazzini.fluxtit.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.fedeturazzini.fluxtit.Controller.Services.ServiceProvider;
import com.app.fedeturazzini.fluxtit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPets(true);
    }

    public void requestPets (Boolean available) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.getPets(true);
    }

}
