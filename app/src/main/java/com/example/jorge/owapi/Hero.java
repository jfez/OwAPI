package com.example.jorge.owapi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity


public class Hero implements Parcelable {

    @NonNull
    @PrimaryKey
    public String name;

    public String role;

    public int numberSkins;
    public int numberEmotes;
    public int numberSprays;


    protected Hero(Parcel in) {
        name = in.readString();
        role = in.readString();
        numberSkins = in.readInt();
        numberEmotes = in.readInt();
        numberSprays = in.readInt();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel in) {
            return new Hero(in);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }

    public Hero(){

    }

    public Hero(String name, String role, int numberSkins, int numberEmotes, int numberSprays){
        this.name = name;
        this.role = role;
        this.numberSkins = numberSkins;
        this.numberEmotes = numberEmotes;
        this.numberSprays = numberSprays;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(role);
        dest.writeInt(numberSkins);
        dest.writeInt(numberEmotes);
        dest.writeInt(numberSprays);
    }
}
