package com.purpletealabs.bitcoinapp.datasource

import android.util.Log

import com.google.gson.Gson
import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService
import com.purpletealabs.bitcoinapp.dtos.DefaultPriceListResponse
import com.purpletealabs.bitcoinapp.dtos.Price

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import rx.schedulers.Schedulers

class PriceDataSourceNetwork : IPriceDataSource {
    internal var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun cancel() {
        unSubscribeFromObservable()
        compositeDisposable = null
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
        }
    }


    override fun getDefaultPriceList(callback: IPriceDataSource.Callback) {
        val service = CoinDeskServiceFactory.newServiceInstance()
        val call = service.defaultPriceList
        val disposable = call.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ defaultPriceListResponse ->
            if (defaultPriceListResponse != null) {
                val prices = ArrayList<Price>()
                prices.add(defaultPriceListResponse.bpi?.usdPrice!!)
                prices.add(defaultPriceListResponse.bpi.gbpPrice!!)
                prices.add(defaultPriceListResponse.bpi.eurPrice!!)
                callback.onGetDefaultPriceResult(prices)

            } else {
                callback.onGetDefaultPriceFailure()
            }
        }) { callback.onGetDefaultPriceFailure() }
        compositeDisposable!!.add(disposable)
    }

    override fun getPriceForCurrency(currencyCode: String, callback: IPriceDataSource.Callback) {
        val service = CoinDeskServiceFactory.newServiceInstance()
        val call = service.getPriceForCountry(currencyCode)
        val disposable = call.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ responseBody ->
            if (responseBody != null) {
                try {
                    val jsonResponse = responseBody.string()
                    val obj = JSONObject(jsonResponse)
                    val price = Gson().fromJson(obj.getJSONObject("bpi").getString(currencyCode), Price::class.java)
                    callback.onGetCurrencyPriceResult(price)

                } catch (e: IOException) {
                    callback.onGetCurrencyPriceFailure()
                } catch (e: JSONException) {
                    callback.onGetCurrencyPriceFailure()
                }

            } else {
                callback.onGetCurrencyPriceFailure()
            }
        }) { callback.onGetCurrencyPriceFailure() }
        compositeDisposable!!.add(disposable)
    }


}