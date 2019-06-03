package com.example.jorge.owapi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PrivateProfileDialogFragment extends androidx.fragment.app.DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle arguments = getArguments();
        final ProfileSearch profileSearch = (ProfileSearch) arguments.get("ProfileSearch");
        final HeroSearch heroSearch = (HeroSearch) arguments.get("HeroSearch");

        View view = requireActivity().getLayoutInflater().inflate(R.layout.layout_dialog, null);
        TextView battletag = view.findViewById(R.id.battletag);


        try{
            battletag.setText(profileSearch.battletag);
        }

        catch (Exception e){
            battletag.setText(heroSearch.battletag);
        }


        //Hay que hacer otro layout y pasarlo arriba
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //getActivity().finish();
                    }
                });
        builder.setNegativeButton("WHERE?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri url = Uri.parse("https://fotos.subefotos.com/83cd55df301804890b0c58cdade2ad18o.png");
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);

                //getActivity().finish();
            }
        });

        return builder.create();
    }
}
