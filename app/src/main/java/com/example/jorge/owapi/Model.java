package com.example.jorge.owapi;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.android.volley.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import androidx.room.Room;

public class Model {

    static DAO dao;

    private static Model instance;

    private final Resources resources;


    private Model (Context ctx){
        DataBase db = Room.databaseBuilder(ctx,
                DataBase.class, "database-name").build();
        dao = db.GetDAO();
        resources = ctx.getResources();
    }

    public static Model getInstance(Context applicationContext){
        if (instance == null){
            instance = new Model(applicationContext);

        }
        return instance;
    }

    public void addDataBase() {

        InputStream stream = resources.openRawResource(R.raw.paises);
        Scanner scanner = new Scanner(stream);
        List<Country> countries = new ArrayList<>();

        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] arrayS = s.split("#");
            Country country = new Country();
            country.region = Integer.parseInt(arrayS[0]);
            country.name = arrayS[1];
            countries.add(country);
        }

        scanner.close();

        dao.insertCountries(countries);

        stream = resources.openRawResource(R.raw.heroes);
        scanner = new Scanner(stream);
        List<Hero> heroes = new ArrayList<>();

        while (scanner.hasNextLine()){
            String sHero = scanner.nextLine();
            String[] arraySHero = sHero.split("#");
            Hero hero = new Hero();
            hero.name = arraySHero[0];
            hero.role = arraySHero[1];
            hero.numberSkins = Integer.parseInt(arraySHero[2]);
            hero.numberEmotes = Integer.parseInt(arraySHero[3]);
            hero.numberSprays = Integer.parseInt(arraySHero[4]);
            heroes.add(hero);
        }

        scanner.close();

        dao.insertHeroes(heroes);


    }

    public void getCountries(final Response.Listener<Country[]> listener) {
        new AsyncTask<Void, Void, Country[]>(){
            @Override
            protected Country[] doInBackground(Void... voids) {
                Country[] countries = dao.GetCountries();


                if (countries.length == 0){
                    addDataBase();
                    countries = dao.GetCountries();

                }

                return countries;
            }

            @Override
            protected void onPostExecute(Country[] countries) {
                listener.onResponse(countries);
            }
        }.execute();
    }

    public void getHeroes (final Response.Listener<Hero[]> listener) {
        new AsyncTask<Void, Void, Hero[]>(){
            @Override
            protected Hero[] doInBackground(Void... voids) {
                Hero[] heroes = dao.GetHeroes();
                return heroes;
            }

            @Override
            protected void onPostExecute(Hero[] heroes) {
                listener.onResponse(heroes);
            }
        }.execute();
    }



    public void getPlatform(final Response.Listener<Platform[]> listener) {
        new AsyncTask<Void, Void, Platform[]>(){

            @Override
            protected Platform[] doInBackground(Void... voids) {

                Platform [] platforms = Platform.values();

                return platforms;
            }

            @Override
            protected void onPostExecute(Platform[] platforms) {
                listener.onResponse(platforms);
            }
        }.execute();
    }

    Comparator<Country> c = new Comparator<Country>() {
        @Override
        public int compare(Country country, Country input) {
            return country.name.compareTo(input.name);
        }
    };



    public boolean compareCountryBool(String s, Country[] countriesArray) {
        Country input = new Country(s,-1);
        int found = Arrays.binarySearch(countriesArray, input, c);
        return found >= 0;
    }

    public int compareCountryInt(String s, Country[] countriesArray) {
        Country input = new Country(s,-1);
        int found = Arrays.binarySearch(countriesArray, input, c);
        return found;
    }


    Comparator<Hero> h = new Comparator<Hero>() {
        @Override
        public int compare(Hero hero, Hero input) {
            return hero.name.compareTo(input.name);
        }
    };



    public boolean compareHeroBool(String s, Hero[] heroesArray) {
        Hero input = new Hero(s,"",-1,-1,-1);
        int found = Arrays.binarySearch(heroesArray, input, h);
        return found >= 0;
    }

    public int compareHeroInt(String s, Hero[] heroesArray) {
        Hero input = new Hero(s,"",-1,-1,-1);
        int found = Arrays.binarySearch(heroesArray, input, h);
        return found;
    }
}
