package com.example.jorge.owapi;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
    private Button search;
    

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
        search = findViewById(R.id.search);

        heroesAuto.setEnabled(false);
        search.setEnabled(false);




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
                    presenter.onToggleChange(true);
                    //heroes.setBackgroundColor(Color.parseColor("#fafafa"));
                }

                else {
                    heroesAuto.setEnabled(false);
                    heroesAuto.setText("");
                    presenter.onToggleChange(false);
                    //heroes.setBackgroundColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        //quitar el teclado cuando se selecciona un item

        heroesAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });

        countryAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });

        countryAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.checkCountry(s.toString());

            }
        });

        heroesAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.checkHero(s.toString());

            }
        });

        battletagEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onBattletagChange(s.toString());

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.request();
            }
        });



        presenter = new Presenter(Model.getInstance(getApplicationContext()), this);



    }

    @Override
    protected void onResume() {
        super.onResume();

        countryAuto.setText("");
        heroesAuto.setText("");
        battletagEdit.setText("");
        changeProfileHeroesToggle.setChecked(false);
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

    @Override
    public void enableSearch(boolean enableButton) {
        search.setEnabled(enableButton);

    }

    @Override
    public void changeActivity(Platform selectedPlatform, Country selectedCountry, String battletag, Hero selectedHero) {

        Intent intent = new Intent(this, SearchActivity.class);

        intent.putExtra("platform", selectedPlatform);
        intent.putExtra("country", selectedCountry);
        intent.putExtra("battletag", battletag);
        intent.putExtra("hero", selectedHero);



        startActivity(intent);

    }
}
