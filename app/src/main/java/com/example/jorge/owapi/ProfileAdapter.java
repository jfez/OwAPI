package com.example.jorge.owapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class ProfileAdapter extends ArrayAdapter {

    private TextView gamesWonTextView;
    private ImageView imagenPerfil;
    private TextView levelTextView;
    private TextView ratingTextView;
    private TextView cardsTextView;
    private TextView medalsTextView;
    private TextView goldenMedalsTextView;



    public ProfileAdapter(@NonNull Context context, @NonNull List<ProfileSearch> objects) {
        super(context, R.layout.profile_adapter, objects);
    }




    public View getView (int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.profile_adapter, parent, false);
        }

        else {
            view = convertView;
        }

        gamesWonTextView = view.findViewById(R.id.gamesWonInt);
        imagenPerfil = view.findViewById(R.id.imagenPerfil);
        levelTextView = view.findViewById(R.id.levelInt);
        ratingTextView = view.findViewById(R.id.ratingInt);
        cardsTextView = view.findViewById(R.id.cardsInt);
        medalsTextView = view.findViewById(R.id.medalsInt);
        goldenMedalsTextView = view.findViewById(R.id.goldenMedalsInt);


        ProfileSearch profileSearch = (ProfileSearch) getItem(position);

        DownloadImageTask downloadImageTask = new DownloadImageTask(imagenPerfil);

        downloadImageTask.execute(profileSearch.iconURL);

        gamesWonTextView.setText(profileSearch.gamesWon+"");
        levelTextView.setText(profileSearch.level+"");
        ratingTextView.setText(profileSearch.rating+"");
        cardsTextView.setText(profileSearch.cards+"");
        medalsTextView.setText(profileSearch.medals+"");
        goldenMedalsTextView.setText(profileSearch.goldenMedals+"");

        imagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });


        return view;


    }
}
