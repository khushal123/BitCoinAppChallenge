package com.purpletealabs.bitcoinapp.apis

import com.google.gson.GsonBuilder

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object CoinDeskServiceFactory {
    private val API_BASE_URL = "https://api.coindesk.com/v1/bpi/"
    private val CONNECTION_TIMEOUT = 10L
    private val READ_TIMEOUT = 60L
    private val WRITE_TIMEOUT_SECONDS = 60L

    private val okHttpClient: OkHttpClient
        get() {
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            return httpClientBuilder.build()
        }

    fun newServiceInstance(): ICoinDeskService {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(ICoinDeskService::class.java)
    }
}
