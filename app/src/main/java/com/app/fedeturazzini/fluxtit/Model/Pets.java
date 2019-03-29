
package com.app.fedeturazzini.fluxtit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pets {

    @SerializedName("pets")
    @Expose
    private Pets_ pets;

    public Pets_ getPets() {
        return pets;
    }

    public void setPets(Pets_ pets) {
        this.pets = pets;
    }

}
