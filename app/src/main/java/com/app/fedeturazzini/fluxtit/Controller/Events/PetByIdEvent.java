package com.app.fedeturazzini.fluxtit.Controller.Events;

import com.app.fedeturazzini.fluxtit.Model.Pet;

import java.util.List;

public class PetByIdEvent {

    private Pet pet;

    public PetByIdEvent(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }
}
