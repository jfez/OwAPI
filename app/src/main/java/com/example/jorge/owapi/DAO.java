package com.example.jorge.owapi;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAO {

    @Insert
    void insertCountries (List <Country> countries);    //List <Country> countries

    @Insert
    void insertHeroes (List <Hero> heroes);    //List <Hero> heroes

    @Query("SELECT * FROM country ORDER BY name")
    Country[] GetCountries();

    @Query("SELECT * FROM hero ORDER BY name")
    Hero[] GetHeroes();

}
