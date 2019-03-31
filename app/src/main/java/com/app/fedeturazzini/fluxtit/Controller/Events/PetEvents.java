package com.app.fedeturazzini.fluxtit.Controller.Events;

import com.app.fedeturazzini.fluxtit.Model.Pet;

import java.util.List;

public class PetEvents {

    private List<Pet> pets;

    public PetEvents(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }
}
