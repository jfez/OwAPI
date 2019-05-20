package com.example.jorge.owapi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Country implements Parcelable {

    @NonNull
    @PrimaryKey
    public String name;

    public int region;


    protected Country(Parcel in) {
        name = in.readString();
        region = in.readInt();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public Country(){

    }

    public Country(String name, int region){
        this.name = name;
        this.region = region;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(region);
    }
}
