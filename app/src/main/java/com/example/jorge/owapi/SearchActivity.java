package com.example.jorge.owapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements IViewSearch  {

    private ListView listView;

    private List<HeroSearch> listaHeroe = new ArrayList<>();
    private List<ProfileSearch> listaPerfil = new ArrayList<>();

    private ProgressBar progressBar;

    private Platform platform;
    private Country country;
    private String battletag;
    private Hero hero;

    private ActionBar actionBar;

    private PresenterSearch presenterSearch;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        actionBar = getSupportActionBar();

        listView = findViewById(R.id.search);
        progressBar = findViewById(R.id.progressBar);

        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        platform = (Platform) intent.getSerializableExtra("platform");
        country = intent.getParcelableExtra("country");
        battletag = intent.getStringExtra("battletag");
        hero = intent.getParcelableExtra("hero");


        actionBar.setTitle(battletag);
        if (hero != null){
            actionBar.setSubtitle(hero.toString());
        }

        else {
            actionBar.setSubtitle("Profile");
        }

        actionBar.setDisplayHomeAsUpEnabled(true);


        presenterSearch = new PresenterSearch(ModelSearch.getInstance(getApplicationContext()), (IViewSearch) this, platform, country, battletag, hero);


        //No hace falta porque nosotros no queremos sacar diálogo al pulsar sobre ningún campo de la listView
        //Sí hará falta en el caso de que el perfil esté en privado
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StationPrice stationPrice = (StationPrice) listView.getItemAtPosition(position);
                StationPriceDialogFragment stationPriceDialogFragment = new StationPriceDialogFragment();
                Bundle args = new Bundle();
                args.putParcelable("StationPrice", stationPrice);
                stationPriceDialogFragment.setArguments(args);
                stationPriceDialogFragment.show(getSupportFragmentManager(), "SP");
            }
        });*/


    }

    //Habrá que hacer 2 adapters diferentes, uno para los héroes y otro para el perfil

    @Override
    public void showHero(HeroSearch response) {

        //ALGO COMO ESTO

        //Tenemos un adapter de Hero
        //Pero en lugar de pasarle una lista, le pasamos directamente el objeto HeroSearch response
        /*GasPriceAdapter adapter = new GasPriceAdapter(PriceActivity.this, lista);
        listView.setAdapter(adapter);

        //Esto en concreto no tiene sentido porque nuestra lista nunca estará vacía
        if (lista.size()== 0){
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }

        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);*/

    }

    @Override
    public void showProfile(ProfileSearch response) {

        listaPerfil.add(response);


        ProfileAdapter adapter = new ProfileAdapter(SearchActivity.this, listaPerfil);
        listView.setAdapter(adapter);

        if (listaPerfil.size()== 0){
            listView.setEmptyView(findViewById(R.id.empty_list_item));
        }

        listView.setVisibility(View.VISIBLE);
        listView.setEnabled(false);
        progressBar.setVisibility(View.GONE);


    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(),"NETWORK ERROR", Toast.LENGTH_LONG).show();
        finish();

    }

    @Override
    public void showPlayerNotFound() {
        Toast.makeText(getApplicationContext(),"PLAYER NOT FOUND", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showPrivateProfile() {
        progressBar.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PRIVATE PROFILE")
                .setMessage("Go to Overwatch to make it public.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("TUTORIAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Uri url = Uri.parse("https://dotesports.com/overwatch/news/ow-public-private-profile-25347");
                        Intent intent = new Intent(Intent.ACTION_VIEW, url);
                        startActivity(intent);

                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
