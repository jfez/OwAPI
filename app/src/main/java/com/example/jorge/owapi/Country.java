package com.example.jorge.owapi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Country {

    @NonNull
    @PrimaryKey
    public String name;

    public int region;



    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public Country(){

    }


}
