package com.app.fedeturazzini.fluxtit.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.fedeturazzini.fluxtit.Controller.Events.PetByIdEvent;
import com.app.fedeturazzini.fluxtit.Controller.Events.PetEvents;
import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.R;
import com.app.fedeturazzini.fluxtit.View.Adapters.RecyclerPetAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetDetailFragment extends Fragment {

    /** private **/
    private Pet pet;
    private TextView namePet;
    private TextView descPet;
    private TextView infoPet;

    public PetDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pet_detail, container, false);

        /** Link UI **/
        namePet = view.findViewById(R.id.nombrePet);
        descPet = view.findViewById(R.id.description);
        infoPet = view.findViewById(R.id.petinfo);

        return view;
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
    public void onPetByIdEvents(PetByIdEvent petByid) {
        pet = petByid.getPet();

        namePet.setText(pet.getName()); // Name pet
        descPet.setText(String.valueOf(pet.getId())); // Id pet
        infoPet.setText(pet.getStatus()); // Status pet
        pet.getCategory().getName();// Categoru pet
    };
}
