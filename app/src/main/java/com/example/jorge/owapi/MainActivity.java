package com.example.jorge.owapi;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IView{



    private ToggleButton changeProfileHeroes;
    private AutoCompleteTextView heroes;
    private Spinner platform;
    private AutoCompleteTextView country;
    private EditText battletag;
    

    //imageview
    //img.setImageResource(R.drawable.my_image);
    //R.drawable.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeProfileHeroes = findViewById(R.id.buttonHerAcc);
        heroes = findViewById(R.id.autoHeroes);
        platform = findViewById(R.id.spinnerPlat);
        country = findViewById(R.id.autoCountry);
        battletag = findViewById(R.id.battletag);

        heroes.setEnabled(false);
        //heroes.setBackgroundColor(Color.parseColor("#e0e0e0"));

        changeProfileHeroes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    heroes.setEnabled(true);
                    //heroes.setBackgroundColor(Color.parseColor("#fafafa"));
                }

                else {
                    heroes.setEnabled(false);
                    //heroes.setBackgroundColor(Color.parseColor("#e0e0e0"));
                }
            }
        });
    }
}
