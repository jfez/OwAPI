package com.example.jorge.owapi;

import android.os.Parcel;
import android.os.Parcelable;

public class HeroSearch implements Parcelable {

    public String portraitURL;
    public String role;
    public int numberSkins;
    public int eliminations;
    public int eliminationsPF;
    public float damageAVG;
    public int gamesWon;
    public int goldenMedals;
    public String timePlayed;
    public boolean privateProfile;
    public boolean existsProfile;
    public boolean implemented;
    public String heroUrl;

    public HeroSearch(String portraitURL, String role, int numberSkins, int eliminations, int eliminationsPF, float damageAVG, int gamesWon, int goldenMedals, String timePlayed,
                      boolean privateProfile, boolean existsProfile, boolean implemented, String heroUrl) {
        this.portraitURL = portraitURL;
        this.role = role;
        this.numberSkins = numberSkins;
        this.eliminations = eliminations;
        this.eliminationsPF = eliminationsPF;
        this.damageAVG = damageAVG;
        this.gamesWon = gamesWon;
        this.goldenMedals = goldenMedals;
        this.timePlayed = timePlayed;
        this.privateProfile = privateProfile;
        this.existsProfile = existsProfile;
        this.implemented = implemented;
        this.heroUrl = heroUrl;

    }


    protected HeroSearch(Parcel in) {
        portraitURL = in.readString();
        role = in.readString();
        numberSkins = in.readInt();
        eliminations = in.readInt();
        eliminationsPF = in.readInt();
        damageAVG = in.readFloat();
        gamesWon = in.readInt();
        goldenMedals = in.readInt();
        timePlayed = in.readString();
        privateProfile = in.readByte() != 0;
        existsProfile = in.readByte() != 0;
        implemented = in.readByte() != 0;
        heroUrl = in.readString();
    }

    public static final Creator<HeroSearch> CREATOR = new Creator<HeroSearch>() {
        @Override
        public HeroSearch createFromParcel(Parcel in) {
            return new HeroSearch(in);
        }

        @Override
        public HeroSearch[] newArray(int size) {
            return new HeroSearch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(portraitURL);
        dest.writeString(role);
        dest.writeInt(numberSkins);
        dest.writeInt(eliminations);
        dest.writeInt(eliminationsPF);
        dest.writeFloat(damageAVG);
        dest.writeInt(gamesWon);
        dest.writeInt(goldenMedals);
        dest.writeString(timePlayed);
        dest.writeByte((byte) (privateProfile ? 1 : 0));
        dest.writeByte((byte) (existsProfile ? 1 : 0));
        dest.writeByte((byte) (implemented ? 1 : 0));
        dest.writeString(heroUrl);
    }
}
