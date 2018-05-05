package com.purpletealabs.bitcoinapp.viewmodels;

import android.arch.lifecycle.ViewModel;

public class PriceViewModel extends ViewModel {
    private final String price;

    public PriceViewModel(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
