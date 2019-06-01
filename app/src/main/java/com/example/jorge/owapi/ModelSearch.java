package com.example.jorge.owapi;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.DialogFragment;


@SuppressLint("ValidFragment")

public class ModelSearch{

    //"https://ow-api.com/v1/stats/pc/us/XxaviI-2930/profile";
    //"https://ow-api.com/v1/stats/pc/us/XxaviI-2930/heroes/soldier76";
    //"https://playoverwatch.com/en-us/career/pc/XxaviI-2930";
    private String urlProfile1 = "https://ow-api.com/v1/stats/";
    private String urlProfile2 = "/profile";
    private String urlHeroes1 = "https://ow-api.com/v1/stats/";
    private String urlHeroes2 = "/heroes/";
    private String urlProfileWeb = "https://playoverwatch.com/en-us/career/";
    private RequestQueue queue;
    private static ModelSearch instance;

    private ProfileSearch profileSearch;
    private HeroSearch heroSearch;

    private  ModelSearch(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public static ModelSearch getInstance(Context applicationContext) {     //Para hacerlo Singleton
        if (instance == null){
            instance = new ModelSearch(applicationContext);

        }
        return instance;
    }


    public void getProfile(final Platform platform, Country country, String battletag, final Response.Listener<ProfileSearch> listener, final Response.ErrorListener errorListener) {

        String regionOW = "";

        switch (country.region){
            case 1:
                regionOW = "us";
                break;

            case 2:
                regionOW = "eu";
                break;

            case 3:
                regionOW = "asia";
                break;
        }

        String newBattletag = battletag.replace('#','-');

        final String url = urlProfileWeb + platform.getCode() + "/" + newBattletag;

        //la url de búsqueda
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, urlProfile1 + platform.getCode() + "/" + regionOW + "/" + newBattletag + urlProfile2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                profileSearch = parseJSONProfile(response, url);

                if (profileSearch != null){
                    listener.onResponse(profileSearch);
                }

                else {
                    errorListener.onErrorResponse(new VolleyError());


                }



            }
        }, errorListener);
        queue.add(request);
    }


    //mirar el gasPrices
    //si response.length() es 1, significa que el jugador no existe
    //OJO PORQUE SI DEVUELVE NULL, PARECE QUE LO TOMA COMO NETWORK ERROR, HAY QUE INVESTIGAR ESTO
    //TAL VEZ HAY QUE CREAR UN PROFILE/HERO QUE TENGA UN PARÁMETRO ESPECIAL PARA EN EL PRESENTER DETECTAR SI ES UN JUGADOR VÁLIDO O NO
    //si no es 1, el jugador existe; ahora hay que comprobar si tiene el perfil abierto o cerrado
    //lo primer que miramos entonces es JSONArray jsonArray = response.getJSONArray("private");
    //si es true, no buscamos nada más, solo mostramos el diálogo
    //si es false, entonces sí que seguimos buscando en los otros jsonArray para coger las demás stats
    //nosotros no necesitamos ningún bucle for ni tampoco una lista porque solo devolvemos 1 objeto HeroSearch o ProfileSearch
    //así que lo que ha de devolver estos métodos es un objeto HeroSearch o ProfileSearch
    //en el parseJSONHero IGUAL

    //el layout tendrá lugar para las stats (text views) y también para la imagen (del perfil o del heroe) ImageView

    private ProfileSearch parseJSONProfile(JSONObject response, String url) {

        ProfileSearch profile = null;

        JSONObject jsonObject1;
        JSONObject jsonObject2;

        try {
            int index = response.length();


            if (index == 1){
                profile = new ProfileSearch(-1, null, -1, -1, -1, -1, -1, false, false, true, null);
                return profile;
            }

            boolean profilePrivate = (boolean) response.get("private");

            if (profilePrivate){
                //diálogo y tal vez inicializar profile para que no piense que el jugador no existe
                profile = new ProfileSearch(-1, null, -1, -1, -1, -1, -1, true, true, true, null);
            }

            else{
                //buscar los parámetros y crear profile

                //jsonArray = response.getJSONArray("quickPlayStats");
                jsonObject1 = response.getJSONObject("quickPlayStats"); //quickPlayStats
                jsonObject2 = jsonObject1.getJSONObject("awards"); //awards

                int gamesWon = (int) response.get("gamesWon");
                String iconURL = (String) response.get("icon");
                int level = (int) response.get("level");
                int rating = (int) response.get("rating");



                int cards = (int) jsonObject2.get("cards");
                int medals = (int) jsonObject2.get("medals");
                int goldenMedals = (int) jsonObject2.get("medalsGold");


                profile = new ProfileSearch(gamesWon, iconURL, level, rating, cards, medals, goldenMedals, false, true, true, url);

            }
            return profile;
        }
        catch (JSONException e) {
            return null;
        }
    }



    public void getHero(Platform platform, Country country, String battletag, final Hero hero, final Response.Listener<HeroSearch> listener, final Response.ErrorListener errorListener) {
        String regionOW = "";

        switch (country.region){
            case 1:
                regionOW = "us";
                break;

            case 2:
                regionOW = "eu";
                break;

            case 3:
                regionOW = "asia";
                break;
        }

        String newBattletag = battletag.replace('#','-');

        //CAMBIAR NOMBRE DEL HÉROE DONDE HAGA FALTA SI NO COINCIDEN ENTRE PÁGINAS y también tener en cuenta mayúsculas y minúsculas

        String heroName = hero.name;
        String heroNameLowerCase = heroName.toLowerCase();
        String heroNameLowerCaseModified = heroNameLowerCase;

        switch (heroNameLowerCase){
            case "dva":
                heroNameLowerCaseModified = "dVa";
                break;

            case "soldier-76":
                heroNameLowerCaseModified = "soldier76";

        }

        final String url = urlProfileWeb + platform.getCode() + "/" + newBattletag;

        final String finalHeroNameLowerCaseModified = heroNameLowerCaseModified;


        //la url de búsqueda

        JsonRequest request = new JsonObjectRequest(Request.Method.GET, urlHeroes1 + platform.getCode() + "/" + regionOW + "/" + newBattletag + urlHeroes2 + heroNameLowerCase, null, new Response.Listener<JSONObject>() {

            //PASARLE AL parseJSONHero los stats de la base de datos de héroes (rol, skins, emotes y sprays)
            //también pasarle el nombre ya modificado para la URL (puesto con minúsculas o guiones o lo que haga falta)
            @Override
            public void onResponse(JSONObject response) {
                heroSearch = parseJSONHero(response, hero.role, hero.numberSkins, url, finalHeroNameLowerCaseModified);

                if (heroSearch != null){
                    listener.onResponse(heroSearch);
                }

                else {
                    errorListener.onErrorResponse(new VolleyError());


                }



            }
        }, errorListener);
        queue.add(request);
    }

    //el HeroSearch tendrá tambien el rol y skins
    private HeroSearch parseJSONHero(JSONObject response, String role, int numberSkins, String url, String heroNameLowerCaseModified) {

        HeroSearch hero = null;

        JSONObject jsonObject1;
        JSONObject jsonObject2;
        JSONObject jsonObject3;
        JSONObject jsonObject4;
        JSONObject jsonObject5;
        JSONObject jsonObject6;
        JSONObject jsonObject7;
        JSONObject jsonObject8;
        JSONObject jsonObject9;

        try {

            int index = response.length();

            if (index == 1){
                hero = new HeroSearch(null, null, -1, -1, -1, -1, -1, -1,
                        null, false, false, true, null);
                return hero;
            }

            boolean profilePrivate = (boolean) response.get("private");

            if (profilePrivate){
                //diálogo y tal vez inicializar profile para que no piense que el jugador no existe
                hero = new HeroSearch(null, null, -1, -1, -1, -1, -1, -1,
                        null, true, true, true, null);
            }

            else{
                //buscar los parámetros y crear profile

                //jsonArray = response.getJSONArray("quickPlayStats");
                jsonObject1 = response.getJSONObject("quickPlayStats"); //quickPlayStats
                //jsonObject2 = jsonObject1.getJSONObject("awards"); //awards dentro de quickPlayStats
                jsonObject3 = jsonObject1.getJSONObject("careerStats"); //careerStats dentro de quickPlayStats
                jsonObject4 = jsonObject3.getJSONObject(heroNameLowerCaseModified); //el personaje, dentro de careerStats
                jsonObject5 = jsonObject4.getJSONObject("combat"); //combat, dentro del personaje
                //jsonObject6 = jsonObject4.getJSONObject("best"); //best, dentro del personaje
                jsonObject7 = jsonObject4.getJSONObject("average"); //average, dentro del personaje
                jsonObject8 = jsonObject4.getJSONObject("game"); //game, dentro del personaje
                jsonObject9 = jsonObject4.getJSONObject("matchAwards"); //matchAwards, dentro del personaje


                int eliminations = (int) jsonObject5.get("eliminations");
                int eliminationsPF = (int) jsonObject7.get("eliminationsPerLife");
                float damageAVG = (float) jsonObject7.get("allDamageDoneAvgPer10Min");
                int gamesWon = (int) jsonObject8.get("gamesWon");
                int goldenMedals = (int) jsonObject9.get("medalsGold");
                String timePlayed = (String) jsonObject8.get("timePlayed");



                hero = new HeroSearch(null, role, numberSkins, eliminations, eliminationsPF, damageAVG, gamesWon, goldenMedals,
                        timePlayed, false, true, true, url);

            }
            return hero;
        }
        catch (JSONException e) {
            return null;
        }
    }
}
