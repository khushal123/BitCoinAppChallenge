package com.purpletealabs.bitcoinapp.apis

import com.purpletealabs.bitcoinapp.dtos.Currency
import com.purpletealabs.bitcoinapp.dtos.DefaultPriceListResponse

import io.reactivex.Observable
import io.reactivex.Observer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ICoinDeskService {

    @get:GET("supported-currencies.json")
    val supportedCurrencies: Observable<List<Currency>>

    @get:GET("currentprice.json")
    val defaultPriceList: Observable<DefaultPriceListResponse>

    @GET("currentprice/{currency}.json")
    fun getPriceForCountry(@Path("currency") currencyCode: String): Observable<ResponseBody>
}
