package com.example.jorge.owapi;

interface IViewSearch {
    void showHero(HeroSearch response);

    void showProfile(ProfileSearch response);

    void showError(String message);

    void showPlayerNotFound();

    void showPrivateProfile();

    void showNotImplemented();
}

