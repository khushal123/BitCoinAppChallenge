package com.purpletealabs.bitcoinapp.datasource;

import android.support.annotation.NonNull;

import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory;
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService;
import com.purpletealabs.bitcoinapp.dtos.Currency;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CurrencyDataSourceNetwork implements ICurrencyDataSource {
    @Override
    public void getSupportedCurrencies(final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();
        Call<List<Currency>> call = service.getSupportedCurrencies();

        call.enqueue(new retrofit2.Callback<List<Currency>>() {
            @Override
            public void onResponse(@NonNull Call<List<Currency>> call, @NonNull Response<List<Currency>> response) {
                if (response.isSuccessful()) {
                    callback.getCurrenciesResult(response.body());
                } else {
                    callback.getCurrenciesFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Currency>> call, @NonNull Throwable t) {
                callback.getCurrenciesFailure();
            }
        });
    }
}
