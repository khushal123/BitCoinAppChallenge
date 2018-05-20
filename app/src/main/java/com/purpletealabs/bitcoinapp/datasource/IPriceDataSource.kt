package com.purpletealabs.bitcoinapp.datasource

import com.purpletealabs.bitcoinapp.dtos.Price

interface IPriceDataSource {
    fun cancel()
    interface Callback {
        fun onGetDefaultPriceResult(prices: List<Price>)

        fun onGetDefaultPriceFailure()

        fun onGetCurrencyPriceResult(price: Price)

        fun onGetCurrencyPriceFailure()
    }

    fun getDefaultPriceList(callback: Callback)

    fun getPriceForCurrency(currencyCode: String, callback: Callback)
}
