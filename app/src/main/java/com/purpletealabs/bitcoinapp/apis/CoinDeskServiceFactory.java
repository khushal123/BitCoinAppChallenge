package com.purpletealabs.bitcoinapp.apis;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinDeskServiceFactory {
    private static final String API_BASE_URL = "https://api.coindesk.com/v1/bpi/";
    private static final long CONNECTION_TIMEOUT = 10L;
    private static final long READ_TIMEOUT = 60L;
    private static final long WRITE_TIMEOUT_SECONDS = 60L;

    public static ICoinDeskService newServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(getOkHttpClient())
                .build();
        return retrofit.create(ICoinDeskService.class);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return httpClientBuilder.build();
    }
}
