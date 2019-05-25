package com.example.jorge.owapi;

import java.util.List;

interface IViewSearch {
    void showHero(List<HeroSearch> response);

    void showProfile(List<ProfileSearch> response);

    void showError(String message);
}

