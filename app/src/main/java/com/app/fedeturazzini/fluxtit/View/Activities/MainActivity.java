package com.app.fedeturazzini.fluxtit.View.Activities;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.fedeturazzini.fluxtit.Controller.Events.PetEvents;
import com.app.fedeturazzini.fluxtit.Controller.Services.ServiceProvider;
import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.R;
import com.app.fedeturazzini.fluxtit.View.Adapters.RecyclerPetAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** Private **/
    private RecyclerView recyclerViewPet;
    private RecyclerPetAdapter recyclerPetAdapter;
    private List<Pet> petArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Call request **/
        requestPets(true, this);
        /** Link UI **/
        recyclerViewPet = findViewById(R.id.recyclerViewPet);
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        /** Swipe to refresh request **/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout.isRefreshing() == true) {
                    requestPets(true, getApplicationContext());
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /** Event bus **/
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPetsEvents(PetEvents pets) {
        petArrayList = pets.getPets();

        recyclerPetAdapter = new RecyclerPetAdapter(petArrayList, this);
        recyclerViewPet.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewPet.setAdapter(recyclerPetAdapter);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    };

    /** Request method **/
    public void requestPets (Boolean available, Context context) {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.getPets(true, context);
    }

}
