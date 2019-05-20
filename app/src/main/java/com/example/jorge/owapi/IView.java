package com.example.jorge.owapi;

public interface IView {
    void showPlatforms(Platform[] platforms);

    void showCountries(Country[] countries);

    void showHeroes(Hero[] heroes);

    void enableSearch(boolean enableButton);

    void changeActivity(Platform selectedPlatform, Country selectedCountry, String battletag, Hero selectedHero);
}
