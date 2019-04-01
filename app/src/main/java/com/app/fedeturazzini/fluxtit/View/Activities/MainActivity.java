package com.app.fedeturazzini.fluxtit.View.Activities;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.app.fedeturazzini.fluxtit.Controller.Events.PetEvents;
import com.app.fedeturazzini.fluxtit.Controller.Services.ServiceProvider;
import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.R;
import com.app.fedeturazzini.fluxtit.View.Adapters.RecyclerPetAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    /** Private **/
    private RecyclerView recyclerViewPet;
    private RecyclerPetAdapter recyclerPetAdapter;
    private List<Pet> petArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabButton;
    private boolean flagSort = false;

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

        /** Order list **/
        fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                  orderList();
                } else {
                  orderList();
                }
            }
        });
    }

    /** Menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscador, menu);
        MenuItem item = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (petArrayList != null) {
                    recyclerPetAdapter.setFilter(petArrayList);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /** Search **/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            List<Pet> petList = filter((ArrayList<Pet>) petArrayList, newText);
            recyclerPetAdapter.setFilter(petList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    /** Metodo para filtrar **/
    private List<Pet> filter (ArrayList <Pet> pets, String textIngresado) {
        List<Pet> petListFilter = new ArrayList<>();
        try {
            textIngresado = textIngresado.toLowerCase();
            for (Pet pet: pets) {
                String pet2 = pet.getName().toLowerCase();
                if (pet2.contains(textIngresado)) {
                    petListFilter.add(pet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petListFilter;
    }

    public void orderList () {
        if (petArrayList != null) {
            if (!flagSort) {
                Collections.sort(petArrayList, new Comparator<Pet>() {
                    @Override
                    public int compare(Pet o1, Pet o2) {
                        return Integer.valueOf(o2.getId().compareTo(o1.getId()));
                    }
                });
                recyclerPetAdapter.setPetArrayList(petArrayList);
                flagSort = true;
            } else {
                Collections.sort(petArrayList, new Comparator<Pet>() {
                    @Override
                    public int compare(Pet o1, Pet o2) {
                        return Integer.valueOf(o1.getId().compareTo(o2.getId()));
                    }
                });
                recyclerPetAdapter.setPetArrayList(petArrayList);
                flagSort = false;
            }
        }
    }
}
