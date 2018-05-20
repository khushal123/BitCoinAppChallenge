package com.purpletealabs.bitcoinapp.dtos

import com.google.gson.annotations.SerializedName

class Bpi {
    @SerializedName("USD")
    val usdPrice: Price? = null

    @SerializedName("GBP")
    val gbpPrice: Price? = null

    @SerializedName("EUR")
    val eurPrice: Price? = null
}
