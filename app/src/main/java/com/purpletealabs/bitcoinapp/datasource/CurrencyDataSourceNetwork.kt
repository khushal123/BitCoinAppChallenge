package com.purpletealabs.bitcoinapp.datasource

import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService
import com.purpletealabs.bitcoinapp.dtos.Currency

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response

class CurrencyDataSourceNetwork : ICurrencyDataSource {

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

    override fun getSupportedCurrencies(callback: ICurrencyDataSource.Callback) {
        val service = CoinDeskServiceFactory.newServiceInstance()
        val call = service.supportedCurrencies
        val disposable = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencies ->
                    if (currencies != null) {
                        callback.getCurrenciesResult(currencies)
                    } else {
                        callback.getCurrenciesFailure()
                    }
                }
                ) { callback.getCurrenciesFailure() }
        compositeDisposable!!.add(disposable)
    }


}
