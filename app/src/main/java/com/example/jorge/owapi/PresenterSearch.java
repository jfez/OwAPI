package com.example.jorge.owapi;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

public class PresenterSearch {

    IViewSearch viewSearch;
    ModelSearch modelSearch;


    public PresenterSearch(ModelSearch modelSearch, IViewSearch viewSearch, Platform platform, Country country, String battletag, Hero hero) {
        this.modelSearch = modelSearch;
        this.viewSearch = viewSearch;

        if (hero == null){

            modelSearch.getProfile(platform, country, battletag, new Response.Listener<ProfileSearch>(){

                @Override
                public void onResponse(ProfileSearch response) {
                    onProfileisAvailable(response);

                }
            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            PresenterSearch.this.viewSearch.showError(error.getMessage());

                        }

            });

        }

        else {
            modelSearch.getHero(platform, country, battletag, hero, new Response.Listener<HeroSearch> (){

                @Override
                public void onResponse(HeroSearch response) {
                    onHeroisAvailable(response);

                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    PresenterSearch.this.viewSearch.showError(error.getMessage());
                }
            });
        }




    }

    //solo devolverá 1 objeto HeroSearch o ProfileSearch, no una lista
    //si ese objeto devuelto, es null, será que el response.length() es 1 y devolvemos null indicando que el jugador no existe
    //habrá que comprobar la response aquí
    //si la response no es null, show normal y corriente
    //si la response es null, showPlayerNotFound

    private void onHeroisAvailable(HeroSearch response) {

        viewSearch.showHero(response);

    }

    private void onProfileisAvailable(ProfileSearch response) {

        viewSearch.showProfile(response);

    }


}
