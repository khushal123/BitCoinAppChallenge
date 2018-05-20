package com.purpletealabs.bitcoinapp.dtos

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView

class Price {
    var code: String? = null

    var symbol: String? = null

    var rate: String? = null

    var description: String? = null

    var rateFloat: Float = 0.toFloat()

    @DrawableRes
    var drawable: Int = 0

    companion object {


        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }
    }
}