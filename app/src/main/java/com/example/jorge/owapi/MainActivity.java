package com.example.jorge.owapi;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IView{



    private ToggleButton changeProfileHeroesToggle;
    private AutoCompleteTextView heroesAuto;
    private Spinner platformSpin;
    private AutoCompleteTextView countryAuto;
    private EditText battletagEdit;
    

    //imageview
    //img.setImageResource(R.drawable.my_image);
    //R.drawable.

    private Presenter presenter;

    private ArrayAdapter<Platform> platformArrayAdapter;
    private ArrayAdapter<Country> countryArrayAdapter;
    private ArrayAdapter<Hero> heroesArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeProfileHeroesToggle = findViewById(R.id.buttonHerAcc);
        heroesAuto = findViewById(R.id.autoHeroes);
        platformSpin = findViewById(R.id.spinnerPlat);
        countryAuto = findViewById(R.id.autoCountry);
        battletagEdit = findViewById(R.id.battletag);

        heroesAuto.setEnabled(false);
        //heroes.setBackgroundColor(Color.parseColor("#e0e0e0"));

        platformSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Platform platformSelected = (Platform) platformSpin.getItemAtPosition(position);
                presenter.onPlatformSelected(platformSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        changeProfileHeroesToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    heroesAuto.setEnabled(true);
                    //heroes.setBackgroundColor(Color.parseColor("#fafafa"));
                }

                else {
                    heroesAuto.setEnabled(false);
                    heroesAuto.setText("");
                    //heroes.setBackgroundColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        presenter = new Presenter(Model.getInstance(getApplicationContext()), this);

    }

    @Override
    public void showPlatforms(Platform[] platforms) {
        platformArrayAdapter = new ArrayAdapter<Platform>(this, android.R.layout.simple_spinner_dropdown_item, platforms);
        platformArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        platformSpin.setAdapter(platformArrayAdapter);

    }

    @Override
    public void showCountries(Country[] countries) {

        countryArrayAdapter = new ArrayAdapter<Country>(this, android.R.layout.simple_list_item_1, countries);

        countryAuto.setAdapter(countryArrayAdapter);

    }

    @Override
    public void showHeroes(Hero[] heroes) {
        heroesArrayAdapter = new ArrayAdapter<Hero>(this, android.R.layout.simple_list_item_1, heroes);

        heroesAuto.setAdapter(heroesArrayAdapter);

    }
}
