package com.example.jorge.owapi;

import android.content.Context;
import android.widget.ArrayAdapter;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class HeroAdapter extends ArrayAdapter {

    private ImageView imagenPerfil;
    private TextView roleTextView;
    private TextView numberSkinsTextView;
    private TextView eliminationsTextView;
    private TextView eliminationsPFTextView;
    private TextView damageAVGTextView;
    private TextView gamesWonTextView;
    private TextView goldenMedalsTextView;
    private TextView timePlayedTextView;

    public HeroAdapter(@NonNull Context context, @NonNull List<HeroSearch> objects) {
        super(context, R.layout.hero_adapter, objects);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.hero_adapter, parent, false);
        }

        else {
            view = convertView;
        }

        imagenPerfil = view.findViewById(R.id.imagenPerfil);
        roleTextView = view.findViewById(R.id.rolTextUser);
        numberSkinsTextView = view.findViewById(R.id.skinsInt);
        eliminationsTextView = view.findViewById(R.id.eliminationInt);
        eliminationsPFTextView = view.findViewById(R.id.eliminationsPFInt);
        damageAVGTextView = view.findViewById(R.id.damageAVGInt);
        goldenMedalsTextView = view.findViewById(R.id.goldenMedalsInt);
        gamesWonTextView = view.findViewById(R.id.gamesWonInt);
        timePlayedTextView = view.findViewById(R.id.timePlayedInt);


        final HeroSearch heroSearch = (HeroSearch) getItem(position);

        DownloadImageTask downloadImageTask = new DownloadImageTask(imagenPerfil);

        downloadImageTask.execute(heroSearch.portraitURL);

        roleTextView.setText(heroSearch.role);
        numberSkinsTextView.setText(heroSearch.numberSkins+"");
        eliminationsTextView.setText(heroSearch.eliminations+"");
        eliminationsPFTextView.setText(heroSearch.eliminationsPF+"");
        damageAVGTextView.setText(heroSearch.damageAVG+"");
        goldenMedalsTextView.setText(heroSearch.goldenMedals+"");
        gamesWonTextView.setText(heroSearch.gamesWon+"");
        timePlayedTextView.setText(heroSearch.timePlayed);

        imagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(heroSearch.heroUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });


        return view;


    }
}
