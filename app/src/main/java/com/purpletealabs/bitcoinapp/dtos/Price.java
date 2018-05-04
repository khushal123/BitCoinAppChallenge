package com.purpletealabs.bitcoinapp.dtos;

public class Price {
    private String code;

    private String symbol;

    private String rate;

    private String description;

    private float rateFloat;

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public float getRateFloat() {
        return rateFloat;
    }
}