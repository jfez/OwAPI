package com.example.jorge.owapi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity


public class Hero {

    @NonNull
    @PrimaryKey
    public String name;

    public String role;

    public int numberSkins;
    public int numberEmotes;
    public int numberSprays;




    @Override
    public String toString() {
        return name;
    }

    public Hero(){

    }
}
