
package com.app.fedeturazzini.fluxtit.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pets_ {

    @SerializedName("Pet")
    @Expose
    private ArrayList<Pet> pet = null;

    public ArrayList<Pet> getPet() {
        return pet;
    }

    public void setPet(ArrayList<Pet> pet) {
        this.pet = pet;
    }

}
