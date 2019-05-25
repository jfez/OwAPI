package com.example.jorge.owapi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.DialogFragment;


@SuppressLint("ValidFragment")

public class ModelSearch extends DialogFragment{

    private String url = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipioProducto/";
    private RequestQueue queue;
    private static ModelSearch instance;

    private List<ProfileSearch> profileSearchList;
    private List<HeroSearch> heroSearchList;

    private  ModelSearch(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public static ModelSearch getInstance(Context applicationContext) {     //Para hacerlo Singleton
        if (instance == null){
            instance = new ModelSearch(applicationContext);

        }
        return instance;
    }


    public void getProfile(Platform platform, Country country, String battletag, final Response.Listener<List<ProfileSearch>> listener, final Response.ErrorListener errorListener) {

        //la url de búsqueda
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, url + town.id + "/" + fuel.getCode(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                profileSearchList = parseJSONProfile(response);

                if (profileSearchList != null){
                    listener.onResponse(profileSearchList);
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
    //si no es 1, el jugador existe; ahora hay que comprobar si tiene el perfil abierto o cerrado
    //lo primer que miramos entonces es JSONArray jsonArray = response.getJSONArray("private");
    //si es true, no buscamos nada más, solo mostramos el diálogo
    //si es false, entonces sí que seguimos buscando en los otros jsonArray para coger las demás stats
    //en el parseJSONHero IGUAL

    private List<ProfileSearch> parseJSONProfile(JSONObject response) {

        List<ProfileSearch> profileList = new ArrayList<>();

        try {
            JSONArray jsonArray = response.getJSONArray("ListaEESSPrecio");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject price = jsonArray.getJSONObject(i);

                String rotulo = price.getString("Rótulo");
                String direccion = price.getString("Dirección");
                String precioProducto = price.getString("PrecioProducto");
                String latitud = price.getString("Latitud");
                String longitud = price.getString("Longitud (WGS84)");

                //Double numLatitud = parseDouble(latitud);
                //Double numLongitud = parseDouble(longitud);

                ProfileSearch profile = new ProfileSearch(rotulo, direccion, precioProducto, numLatitud, numLongitud);

                profileList.add(profile);

            }
            return profileList;
        }
        catch (JSONException e) {
            return null;
        }
    }

    public void getHero(Platform platform, Country country, String battletag, Hero hero, final Response.Listener<List<HeroSearch>> listener, final Response.ErrorListener errorListener) {
        //la url de búsqueda
        JsonRequest request = new JsonObjectRequest(Request.Method.GET, url + town.id + "/" + fuel.getCode(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                heroSearchList = parseJSONHero(response);

                if (profileSearchList != null){
                    listener.onResponse(heroSearchList);
                }

                else {
                    errorListener.onErrorResponse(new VolleyError());


                }



            }
        }, errorListener);
        queue.add(request);
    }

    private List<HeroSearch> parseJSONHero(JSONObject response) {

        List<HeroSearch> heroList = new ArrayList<>();

        try {
            JSONArray jsonArray = response.getJSONArray("ListaEESSPrecio");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject price = jsonArray.getJSONObject(i);

                String rotulo = price.getString("Rótulo");
                String direccion = price.getString("Dirección");
                String precioProducto = price.getString("PrecioProducto");
                String latitud = price.getString("Latitud");
                String longitud = price.getString("Longitud (WGS84)");

                //Double numLatitud = parseDouble(latitud);
                //Double numLongitud = parseDouble(longitud);

                HeroSearch hero = new ProfileSearch(rotulo, direccion, precioProducto, numLatitud, numLongitud);

                heroList.add(hero);

            }
            return heroList;
        }
        catch (JSONException e) {
            return null;
        }
    }
}
