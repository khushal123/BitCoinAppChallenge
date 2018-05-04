package com.purpletealabs.bitcoinapp.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;

import com.purpletealabs.bitcoinapp.dtos.Price;

public class MainActivityViewModel extends ViewModel {
    public final MutableLiveData<Boolean> isLoadingData = new MutableLiveData<>();
    public final ObservableArrayList<Price> prices = new ObservableArrayList<>();
}