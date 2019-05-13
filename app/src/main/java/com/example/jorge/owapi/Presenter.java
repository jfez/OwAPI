package com.example.jorge.owapi;

import com.android.volley.Response;

public class Presenter {

    Model model;
    IView view;

    public Presenter(Model model, IView iView) {
        this.model = model;
        this.view = iView;

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
    }

    private void onCountryIsAvailable(Country[] countries) {
        view.showCountries(countries);

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

    }
}
