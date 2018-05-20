package com.purpletealabs.bitcoinapp.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View

import com.purpletealabs.bitcoinapp.R
import com.purpletealabs.bitcoinapp.adapters.PriceListAdapter
import com.purpletealabs.bitcoinapp.databinding.ActivityMainBinding
import com.purpletealabs.bitcoinapp.datasource.IPriceDataSource
import com.purpletealabs.bitcoinapp.datasource.PriceDataSourceNetwork
import com.purpletealabs.bitcoinapp.dtos.Price
import com.purpletealabs.bitcoinapp.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity(), IPriceDataSource.Callback {

    private var mViewModel: MainActivityViewModel? = null

    private var mSelectedCurrency: String? = null

    private var mDataSource: IPriceDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())


        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        binding.vm = mViewModel

        setupToolbar()

        initViews(binding)

        if (mViewModel!!.prices.isEmpty()) {
            getDefaultPriceList()
        } else {
            mViewModel!!.isLoadingData.set(false)
        }
    }

    private fun getDefaultPriceList() {
        mViewModel!!.isLoadingData.set(true)
        if (mDataSource == null)
            mDataSource = PriceDataSourceNetwork()
        mDataSource!!.getDefaultPriceList(this)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initViews(binding: ActivityMainBinding) {
        binding.rvPrices.layoutManager = LinearLayoutManager(this)
        binding.rvPrices.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = PriceListAdapter(this, mViewModel!!.prices)
        binding.rvPrices.adapter = adapter
    }

    fun onFabClicked(view: View) {
        val intent = Intent(this, SelectCountryActivity::class.java)
        startActivityForResult(intent, REQ_SELECT_COUNTRY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_SELECT_COUNTRY -> {
                    mSelectedCurrency = data.getStringExtra(SelectCountryActivity.EXTRA_SELECTED_CURRENCY)
                    getPricesForSelectedCurrency()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getPricesForSelectedCurrency() {
        mViewModel!!.isLoadingData.set(true)
        if (mDataSource == null)
            mDataSource = PriceDataSourceNetwork()
        mDataSource!!.getPriceForCurrency(mSelectedCurrency!!, this)
    }

    override fun onGetDefaultPriceResult(prices: List<Price>) {
        mViewModel!!.defaultPrices.clear()
        mViewModel!!.defaultPrices.addAll(prices)
        mViewModel!!.prices.clear()
        mViewModel!!.prices.addAll(mViewModel!!.defaultPrices)
        mViewModel!!.isLoadingData.set(false)
    }

    override fun onGetDefaultPriceFailure() {
        mViewModel!!.isLoadingData.set(false)
        mViewModel!!.prices.clear()
        showSnackBar(getString(R.string.generic_error))
    }

    override fun onGetCurrencyPriceResult(price: Price) {
        mViewModel!!.prices.clear()
        mViewModel!!.prices.add(price)
        mViewModel!!.prices.addAll(mViewModel!!.defaultPrices)
        mViewModel!!.isLoadingData.set(false)
    }

    override fun onGetCurrencyPriceFailure() {
        mViewModel!!.isLoadingData.set(false)
        mViewModel!!.prices.clear()
        showSnackBar(getString(R.string.generic_error))
    }


    override fun onDestroy() {
        super.onDestroy()
        mDataSource!!.cancel()
    }

    companion object {
        @JvmStatic
        private val REQ_SELECT_COUNTRY = 1
    }
}