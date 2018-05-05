package com.purpletealabs.bitcoinapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.purpletealabs.bitcoinapp.dtos.Currency;

public class SelectCountryViewModel extends ViewModel {
    public final ObservableBoolean isLoadingData = new ObservableBoolean();
    public final ObservableArrayList<Currency> countries = new ObservableArrayList<>();
}