package com.example.jorge.owapi;

import androidx.room.Entity;

@Entity

public enum Platform {

    PC ("PC","pc"),
    PS4 ("PlayStation 4","psn"),
    XBOX ("Xbox One","xbl");



    private String label;
    private String code;

    /*public int getCode() {
        return code;
    }*/

    Platform(String toString, String platform) {
        label = toString;
        code = platform;
    }

    @Override
    public String toString() {
        return label;
    }


}
