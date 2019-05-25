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

            modelSearch.getProfile(platform, country, battletag, new Response.Listener<List<ProfileSearch>>(){

                @Override
                public void onResponse(List<ProfileSearch> response) {
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
            modelSearch.getHero(platform, country, battletag, hero, new Response.Listener<List<HeroSearch>> (){

                @Override
                public void onResponse(List<HeroSearch> response) {
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

    private void onHeroisAvailable(List<HeroSearch> response) {

        viewSearch.showHero(response);

    }

    private void onProfileisAvailable(List<ProfileSearch> response) {

        viewSearch.showProfile(response);

    }


}
