package com.purpletealabs.bitcoinapp.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.purpletealabs.bitcoinapp.R
import com.purpletealabs.bitcoinapp.adapters.CountryListAdapter
import com.purpletealabs.bitcoinapp.databinding.ActivitySelectCountryBinding
import com.purpletealabs.bitcoinapp.datasource.CurrencyDataSourceNetwork
import com.purpletealabs.bitcoinapp.datasource.ICurrencyDataSource
import com.purpletealabs.bitcoinapp.dtos.Currency
import com.purpletealabs.bitcoinapp.viewmodels.SelectCountryViewModel

class SelectCountryActivity : BaseActivity(), ICurrencyDataSource.Callback, CountryListAdapter.ClickListener {

    private var mViewModel: SelectCountryViewModel? = null

    private var mDataSource: ICurrencyDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySelectCountryBinding>(this, R.layout.activity_select_country)

        mViewModel = ViewModelProviders.of(this).get(SelectCountryViewModel::class.java)

        binding.vm = mViewModel

        setupToolbar()

        initViews(binding)

        getCountryList()
    }

    private fun getCountryList() {
        mViewModel!!.isLoadingData.set(true)
        if (mDataSource == null)
            mDataSource = CurrencyDataSourceNetwork()
        mDataSource!!.getSupportedCurrencies(this)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews(binding: ActivitySelectCountryBinding) {
        binding.rvCountryList.layoutManager = LinearLayoutManager(this)
        binding.rvCountryList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = CountryListAdapter(this, mViewModel!!.countries, this)
        binding.rvCountryList.adapter = adapter
    }


    override fun getCurrenciesFailure() {
        mViewModel!!.isLoadingData.set(false)
        mViewModel!!.countries.clear()
    }

    override fun getCurrenciesResult(countries: List<Currency>) {
        mViewModel!!.isLoadingData.set(false)
        mViewModel!!.countries.clear()
        mViewModel!!.countries.addAll(countries)
    }

    override fun onCountrySelected(currency: Currency) {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_SELECTED_CURRENCY, currency.currency)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_CANCELED, resultIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (mDataSource != null)
            mDataSource!!.cancel()
        super.onDestroy()
    }


    companion object {
        @JvmStatic
        val EXTRA_SELECTED_CURRENCY = "currency"
    }
}