package com.purpletealabs.bitcoinapp.viewmodels;

public class CountryViewModel {
    private final String code;
    private final String name;

    public CountryViewModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}