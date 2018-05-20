package com.purpletealabs.bitcoinapp.datasource;

import com.purpletealabs.bitcoinapp.dtos.Currency;

import java.util.List;

public interface ICurrencyDataSource {
    void cancel();
    interface Callback {
        void getCurrenciesFailure();

        void getCurrenciesResult(List<Currency> countries);
    }

    void getSupportedCurrencies(Callback callback);
}