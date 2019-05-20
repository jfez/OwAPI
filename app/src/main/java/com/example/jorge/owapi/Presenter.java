package com.example.jorge.owapi;

import com.android.volley.Response;

public class Presenter {

    Model model;
    IView view;
    Country[] countriesArray;
    Hero[] heroesArray;
    Country selectedCountry;
    Hero selectedHero;
    Platform selectedPlatform;
    String battletag;
    boolean heroesActivated;

    boolean countryCheck;
    boolean heroCheck;

    public Presenter(Model model, IView iView) {
        this.model = model;
        this.view = iView;

        heroesActivated = false;
        countryCheck = false;
        heroCheck = false;

        model.getPlatform(new Response.Listener<Platform[]>(){

            @Override
            public void onResponse(Platform[] response) {
                onPlatformisAvailable(response);
            }
        });

        model.getCountries(new Response.Listener<Country[]>(){
            @Override
            public void onResponse(Country[] response) {
                onCountryIsAvailable(response);

            }
        });




    }

    private void onHeroesAreAvailable(Hero[] heroes) {
        view.showHeroes(heroes);
        heroesArray = heroes;
    }

    private void onCountryIsAvailable(Country[] countries) {
        view.showCountries(countries);
        countriesArray = countries;

        model.getHeroes(new Response.Listener<Hero[]>(){
            @Override
            public void onResponse(Hero[] response) {
                onHeroesAreAvailable(response);

            }
        });
    }

    private void onPlatformisAvailable(Platform[] platforms) {
        view.showPlatforms(platforms);
    }

    public void onPlatformSelected(Platform platformSelected) {
        selectedPlatform = platformSelected;

    }

    public void checkCountry(String s) {
        if (countriesArray == null){
            return;
        }
        boolean enableButton = model.compareCountryBool (s, countriesArray);
        if (enableButton){
            int index = model.compareCountryInt(s, countriesArray);
            selectedCountry = countriesArray[index];
            countryCheck = true;
        }

        else {
            countryCheck = false;
        }

        if (!heroesActivated){
            view.enableSearch(enableButton);
        }

        else{
            if (enableButton){
                if(!heroCheck){
                    enableButton = false;
                }
            }

            view.enableSearch(enableButton);
        }


    }

    public void checkHero(String s) {
        if (heroesArray == null){
            return;
        }
        boolean enableButton = model.compareHeroBool (s, heroesArray);
        if (enableButton){
            int index = model.compareHeroInt(s, heroesArray);
            selectedHero = heroesArray[index];
            heroCheck = true;

            if (!countryCheck){
                enableButton = false;
            }

            view.enableSearch(enableButton);
        }

        else{
            heroCheck = false;
            view.enableSearch(enableButton);
        }


    }

    public void onToggleChange(boolean b) {
        heroesActivated = b;

        if (heroesActivated){
            view.enableSearch(false);
        }

        else{
            view.enableSearch(countryCheck);
        }
    }

    public void request() {
        if (heroesActivated){
            view.changeActivity(selectedPlatform, selectedCountry, battletag, selectedHero);
        }

        else{
            view.changeActivity(selectedPlatform, selectedCountry, battletag, null);
        }
    }

    public void onBattletagChange(String s) {
        battletag = s;

    }
}
