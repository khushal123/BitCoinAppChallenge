package com.purpletealabs.bitcoinapp.datasource;

import com.purpletealabs.bitcoinapp.dtos.Price;

import java.util.List;

public interface IPriceDataSource {
    interface Callback {
        void onGetDefaultPriceResult(List<Price> prices);

        void onGetDefaultPriceFailure();

        void onGetCurrencyPriceResult(Price price);

        void onGetCurrencyPriceFailure();
    }

    void getDefaultPriceList(Callback callback);

    void getPriceForCurrency(String currencyCode, Callback callback);
}
