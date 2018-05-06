package com.purpletealabs.bitcoinapp.viewmodels;

public class CountryViewModel {
    private final String code;
    private final String name;
    private final int drawable;

    public CountryViewModel(String code, String name, int drawable) {
        this.code = code;
        this.name = name;
        this.drawable = drawable;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }
}