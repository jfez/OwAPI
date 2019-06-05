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

            if (platform == Platform.PS4 || platform == Platform.XBOX){
                viewSearch.showNotImplemented();
                return;
            }

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
            if (platform == Platform.PS4 || platform == Platform.XBOX){
                viewSearch.showNotImplemented();
                return;
            }

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



    private void onHeroisAvailable(HeroSearch response) {

        if(!response.existsProfile){
            viewSearch.showPlayerNotFound();
        }

        else {

            if(response.privateProfile){
                viewSearch.showPrivateProfile(null, response);
            }

            else{
                viewSearch.showHero(response);
            }
        }

    }

    private void onProfileisAvailable(ProfileSearch response) {
        if(!response.existsProfile){
            viewSearch.showPlayerNotFound();
        }

        else {

            if(response.privateProfile){
                viewSearch.showPrivateProfile(response, null);
            }

            else{
                viewSearch.showProfile(response);
            }
        }

    }


}
