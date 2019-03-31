package com.app.fedeturazzini.fluxtit.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.R;
import com.app.fedeturazzini.fluxtit.View.Activities.PetDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPetAdapter extends RecyclerView.Adapter<RecyclerPetAdapter.MyViewHolder> {

    private List<Pet> petArrayList;
    private Context context;

    // Constructor
    public RecyclerPetAdapter(List<Pet> petArrayList, Context context) {
        this.petArrayList = petArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerPetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_pet_cell, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPetAdapter.MyViewHolder myViewHolder, int i) {

        final Pet pet = petArrayList.get(i);

        /** Pet Name **/
        if (pet.getName() != null) {
            myViewHolder.petName.setText(pet.getName());
        } else {
            myViewHolder.petName.setText("Nombre no disponible");
        }

        /** Pet ID **/
        if (!pet.getId().equals(null)) {
            myViewHolder.petId.setText("ID: " + pet.getId());
        } else {
            myViewHolder.petId.setText("ID: " + "id no disponible");
        }

        /** Pet available **/
        if (pet.getStatus().equals("available")) {
            myViewHolder.petAvailable.setText(R.string.available);
            myViewHolder.petAvailable.setTextColor(context.getResources().getColor(R.color.colorAvailable));
        } else {
            myViewHolder.petAvailable.setText(R.string.not_available);
            myViewHolder.petAvailable.setTextColor(context.getResources().getColor(R.color.colorNotAvailable));
        }

        /** Picasso for images **/
        Picasso.get().load(R.drawable.pet_icon_foreground)
                .resize(200,200)
                .placeholder(R.drawable.pet_icon_foreground)
                .into(myViewHolder.petImage);


        myViewHolder.linearLayoutCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong(PetDetailActivity.PET_ID, pet.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petArrayList.size();
    }

    public void setFilter (List<Pet> listPetFiltrada) {
        this.petArrayList = new ArrayList<>();
        this.petArrayList = listPetFiltrada;
        notifyDataSetChanged();
    }

    // View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView petName;
        public TextView petId;
        public TextView petAvailable;
        public ImageView petImage;
        public LinearLayout linearLayoutCell;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.nameTextView);
            petId = itemView.findViewById(R.id.idPet);
            petAvailable = itemView.findViewById(R.id.availableTextView);
            petImage = itemView.findViewById(R.id.petImage);
            linearLayoutCell = itemView.findViewById(R.id.linearLayoutCell);
        }
    }
}
