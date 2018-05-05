package com.purpletealabs.bitcoinapp.dtos;

import com.google.gson.annotations.SerializedName;

public class Bpi {
    @SerializedName("USD")
    private Price usdPrice;

    @SerializedName("GBP")
    private Price gbpPrice;

    @SerializedName("EUR")
    private Price eurPrice;

    public Price getUsdPrice() {
        return usdPrice;
    }

    public Price getGbpPrice() {
        return gbpPrice;
    }

    public Price getEurPrice() {
        return eurPrice;
    }
}
