package com.example.jorge.owapi;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

public interface DAO {

    @Insert
    void insertCountries ();    //List <Country> countries

    @Insert
    void insertHeroes ();    //List <Hero> heroes

}
