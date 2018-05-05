package com.purpletealabs.bitcoinapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.purpletealabs.bitcoinapp.dtos.Price;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    public final ObservableBoolean isLoadingData = new ObservableBoolean();
    public final ObservableArrayList<Price> prices = new ObservableArrayList<>();
    public final List<Price> defaultPrices = new ArrayList<>();
}