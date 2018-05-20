package com.purpletealabs.bitcoinapp.dtos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DefaultPriceListResponse {

    @SerializedName("bpi")
    @Expose
    val bpi: Bpi? = null
}
