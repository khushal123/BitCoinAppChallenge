package com.purpletealabs.bitcoinapp.viewmodels;

import android.arch.lifecycle.ViewModel;

public class PriceViewModel extends ViewModel {
    private final String price;
    int drawable;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public PriceViewModel(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
