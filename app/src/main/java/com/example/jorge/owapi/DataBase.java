package com.example.jorge.owapi;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class, Hero.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract DAO GetDAO();
}
