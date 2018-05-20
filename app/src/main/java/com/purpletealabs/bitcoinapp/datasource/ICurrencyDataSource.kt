package com.purpletealabs.bitcoinapp.datasource

import com.purpletealabs.bitcoinapp.dtos.Currency

interface ICurrencyDataSource {
    fun cancel()
    interface Callback {
        fun getCurrenciesFailure()

        fun getCurrenciesResult(countries: List<Currency>)
    }

    fun getSupportedCurrencies(callback: Callback)
}