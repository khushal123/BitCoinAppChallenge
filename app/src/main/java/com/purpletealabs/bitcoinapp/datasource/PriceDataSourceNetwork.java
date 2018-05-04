package com.purpletealabs.bitcoinapp.datasource;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory;
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService;
import com.purpletealabs.bitcoinapp.dtos.DefaultPriceListResponse;
import com.purpletealabs.bitcoinapp.dtos.Price;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceDataSourceNetwork implements IPriceDataSource {

    @Override
    public void getDefaultPriceList(final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();

        Call<DefaultPriceListResponse> call = service.getDefaultPriceList();

        call.enqueue(new retrofit2.Callback<DefaultPriceListResponse>() {
            @Override
            public void onResponse(@NonNull Call<DefaultPriceListResponse> call, @NonNull Response<DefaultPriceListResponse> response) {
                if (response.isSuccessful()) {
                    DefaultPriceListResponse dpl = response.body();
                    List<Price> prices = new ArrayList<>();
                    prices.add(dpl.getBpi().getUsdPrice());
                    prices.add(dpl.getBpi().getGbpPrice());
                    prices.add(dpl.getBpi().getEurPrice());
                    callback.onGetDefaultPriceResult(prices);

                } else {
                    callback.onGetDefaultPriceFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DefaultPriceListResponse> call, @NonNull Throwable t) {
                callback.onGetDefaultPriceFailure();
            }
        });
    }

    @Override
    public void getPriceForCurrency(final String currencyCode, final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();
        Call<ResponseBody> call = service.getPriceForCountry(currencyCode);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject obj = new JSONObject(jsonResponse);
                        Price price = new Gson().fromJson(obj.getJSONObject("bpi").getString(currencyCode), Price.class);
                        callback.onGetCurrencyPriceResult(price);

                    } catch (IOException | JSONException e) {
                        callback.onGetCurrencyPriceFailure();
                    }
                } else {
                    callback.onGetCurrencyPriceFailure();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onGetCurrencyPriceFailure();
            }
        });
    }
}