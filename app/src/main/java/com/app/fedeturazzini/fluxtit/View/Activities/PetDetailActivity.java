package com.app.fedeturazzini.fluxtit.View.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.fedeturazzini.fluxtit.Controller.Services.ServiceProvider;
import com.app.fedeturazzini.fluxtit.R;
import com.app.fedeturazzini.fluxtit.View.Fragments.GoogleFragment;
import com.app.fedeturazzini.fluxtit.View.Fragments.PetDetailFragment;

public class PetDetailActivity extends AppCompatActivity {

    /** Public **/
    public static String PET_ID = "pet_id";

    /** Private **/
    private Long petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        getIncomingIntents();
        requestPetId(petId);

        /** Fragment linking **/
        PetDetailFragment petDetailFragment = new PetDetailFragment();
        GoogleFragment googleFragment = new GoogleFragment();
        putFragmentFirstContainer(petDetailFragment);
        putFragmentSecondContainer(googleFragment);
    }

    /** Request method **/
    public void requestPetId(Long id) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.getPetId(String.valueOf(id), this);
    }

    /** Intent recieve method **/
    private void getIncomingIntents () {
        Intent intentRecieve = getIntent();
        Bundle bundleRecieve = intentRecieve.getExtras();
        petId = bundleRecieve.getLong(PET_ID);
    }

    public void putFragmentFirstContainer (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void putFragmentSecondContainer (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragment2, fragment);
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
