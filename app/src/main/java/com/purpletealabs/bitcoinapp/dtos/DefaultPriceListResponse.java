package com.purpletealabs.bitcoinapp.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultPriceListResponse {

    @SerializedName("bpi")
    @Expose
    private Bpi bpi;

    public Bpi getBpi() {
        return bpi;
    }
}
