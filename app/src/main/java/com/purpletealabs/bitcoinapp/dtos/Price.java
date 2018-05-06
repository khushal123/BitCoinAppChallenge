package com.purpletealabs.bitcoinapp.dtos;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class Price {
    private String code;

    private String symbol;

    private String rate;

    private String description;

    private float rateFloat;

    @DrawableRes
    private int drawable;

    public void setCode(String code) {
        this.code = code;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRateFloat(float rateFloat) {
        this.rateFloat = rateFloat;
    }

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

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }


    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }
}