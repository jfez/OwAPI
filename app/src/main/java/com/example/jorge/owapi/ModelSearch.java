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

public class ModelSearch extends DialogFragment{

    //"https://ow-api.com/v1/stats/pc/us/XxaviI-2930/profile";
    //"https://ow-api.com/v1/stats/pc/us/XxaviI-2930/heroes/soldier76";
    private String urlProfile1 = "https://ow-api.com/v1/stats/";
    private String urlProfile2 = "/profile";
    private String urlHeroes1 = "https://ow-api.com/v1/stats/";
    private String urlHeroes2 = "/heroes/";
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


    public void getProfile(Platform platform, Country country, String battletag, final Response.Listener<ProfileSearch> listener, final Response.ErrorListener errorListener) {

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

        //la url de búsqueda
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, urlProfile1 + platform.getCode() + "/" + regionOW + "/" + newBattletag + urlProfile2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                profileSearch = parseJSONProfile(response);

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

    private ProfileSearch parseJSONProfile(JSONObject response) {

        ProfileSearch profile = null;
        JSONArray jsonArray;
        JSONObject jsonObject;

        try {
            int index = response.length();

            if (index == 1){
                return profile;
            }

            boolean profilePrivate = (boolean) response.get("private");

            if (profilePrivate){
                //diálogo y tal vez inicializar profile para que no piense que el jugador no existe
            }

            else{
                //buscar los parámetros y crear profile
            }

            jsonArray = response.getJSONArray("quickPlayStats");

            jsonObject = jsonArray.getJSONObject(0); //awards



            /*String rotulo = price.getString("Rótulo");
            String direccion = price.getString("Dirección");
            String precioProducto = price.getString("PrecioProducto");
            String latitud = price.getString("Latitud");
            String longitud = price.getString("Longitud (WGS84)");

            Double numLatitud = parseDouble(latitud);
            Double numLongitud = parseDouble(longitud);*/

            //profile = new ProfileSearch(rotulo, direccion, precioProducto, numLatitud, numLongitud);




            return profile;
        }
        catch (JSONException e) {
            return null;
        }
    }



    public void getHero(Platform platform, Country country, String battletag, Hero hero, final Response.Listener<HeroSearch> listener, final Response.ErrorListener errorListener) {
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

        //la url de búsqueda
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, urlHeroes1 + platform.getCode() + "/" + regionOW + "/" + newBattletag + urlHeroes2 + hero.toString(), null, new Response.Listener<JSONObject>() {

            //PASARLE AL parseJSONHero los stats de la base de datos de héroes (rol, skins, emotes y sprays)
            //también pasarle el nombre ya modificado para la URL (puesto con minúsculas o guiones o lo que haga falta)
            @Override
            public void onResponse(JSONObject response) {
                heroSearch = parseJSONHero(response);

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

    //el HeroSearch tendrá tambien el rol, emotes, sprays y skins
    private HeroSearch parseJSONHero(JSONObject response) {

        HeroSearch hero = null;
        JSONArray jsonArray;
        JSONObject jsonObject;

        try {
            int index = response.length();

            if (index == 1){
                return hero;
            }

            boolean profilePrivate = (boolean) response.get("private");

            if (profilePrivate){
                //diálogo y tal vez inicializar hero para que no piense que el jugador no existe
            }

            else{
                //buscar los parámetros y crear hero
            }

            //Buscamos lo que toque del JSON

            //jsonArray = response.getJSONArray("quickPlayStats");

            //jsonObject = jsonArray.getJSONObject(0); //awards



            /*String rotulo = price.getString("Rótulo");
            String direccion = price.getString("Dirección");
            String precioProducto = price.getString("PrecioProducto");
            String latitud = price.getString("Latitud");
            String longitud = price.getString("Longitud (WGS84)");

            Double numLatitud = parseDouble(latitud);
            Double numLongitud = parseDouble(longitud);*/

            //hero = new HeroSearch(rotulo, direccion, precioProducto, numLatitud, numLongitud);




            return hero;
        }
        catch (JSONException e) {
            return null;
        }
    }
}
