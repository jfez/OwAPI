package com.example.jorge.owapi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProfileSearch implements Parcelable {


    public String iconURL;
    public int level;
    public int gamesWon;
    public int rating;
    public int cards;
    public int medals;
    public int goldenMedals;
    public boolean privateProfile;
    public boolean existsProfile;



    public ProfileSearch(int gamesWon, String iconURL, int level, int rating, int cards, int medals, int goldenMedals, boolean privateProfile, boolean existsProfile) {
        this.gamesWon = gamesWon;
        this.iconURL = iconURL;
        this.level = level;
        this.rating = rating;
        this.cards = cards;
        this.medals = medals;
        this.goldenMedals = goldenMedals;
        this.privateProfile = privateProfile;
        this.existsProfile = existsProfile;
    }

    protected ProfileSearch(Parcel in) {
        iconURL = in.readString();
        level = in.readInt();
        gamesWon = in.readInt();
        rating = in.readInt();
        cards = in.readInt();
        medals = in.readInt();
        goldenMedals = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconURL);
        dest.writeInt(level);
        dest.writeInt(gamesWon);
        dest.writeInt(rating);
        dest.writeInt(cards);
        dest.writeInt(medals);
        dest.writeInt(goldenMedals);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileSearch> CREATOR = new Creator<ProfileSearch>() {
        @Override
        public ProfileSearch createFromParcel(Parcel in) {
            return new ProfileSearch(in);
        }

        @Override
        public ProfileSearch[] newArray(int size) {
            return new ProfileSearch[size];
        }
    };
}
