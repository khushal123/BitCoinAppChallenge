package com.purpletealabs.bitcoinapp.apis;

import com.purpletealabs.bitcoinapp.dtos.Currency;
import com.purpletealabs.bitcoinapp.dtos.DefaultPriceListResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICoinDeskService {

    @GET("supported-currencies.json")
    Call<List<Currency>> getSupportedCurrencies();

    @GET("currentprice.json")
    Call<DefaultPriceListResponse> getDefaultPriceList();

    @GET("currentprice/{currency}.json")
    Call<ResponseBody> getPriceForCountry(@Path("currency") String currencyCode);
}
