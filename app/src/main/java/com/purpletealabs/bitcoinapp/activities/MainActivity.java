package com.purpletealabs.bitcoinapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.purpletealabs.bitcoinapp.R;
import com.purpletealabs.bitcoinapp.adapters.PriceListAdapter;
import com.purpletealabs.bitcoinapp.databinding.ActivityMainBinding;
import com.purpletealabs.bitcoinapp.datasource.IPriceDataSource;
import com.purpletealabs.bitcoinapp.datasource.PriceDataSourceNetwork;
import com.purpletealabs.bitcoinapp.dtos.Price;
import com.purpletealabs.bitcoinapp.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements IPriceDataSource.Callback {

    private static final int REQ_SELECT_COUNTRY = 1;

    private MainActivityViewModel mViewModel;

    private String mSelectedCurrency;

    private IPriceDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding.setVm(mViewModel);

        setupToolbar();

        initViews(binding);

        if (mViewModel.prices.isEmpty()) {
            getDefaultPriceList();
        } else {
            mViewModel.isLoadingData.set(false);
        }
    }

    private void getDefaultPriceList() {
        mViewModel.isLoadingData.set(true);
        if (mDataSource == null)
            mDataSource = new PriceDataSourceNetwork();
        mDataSource.getDefaultPriceList(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews(ActivityMainBinding binding) {
        binding.rvPrices.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPrices.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        PriceListAdapter adapter = new PriceListAdapter(this, mViewModel.prices);
        binding.rvPrices.setAdapter(adapter);
    }

    public void onFabClicked(View view) {
        Intent intent = new Intent(this, SelectCountryActivity.class);
        startActivityForResult(intent, REQ_SELECT_COUNTRY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_SELECT_COUNTRY:
                    mSelectedCurrency = data.getStringExtra(SelectCountryActivity.EXTRA_SELECTED_CURRENCY);
                    getPricesForSelectedCurrency();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getPricesForSelectedCurrency() {
        mViewModel.isLoadingData.set(true);
        if (mDataSource == null)
            mDataSource = new PriceDataSourceNetwork();
        mDataSource.getPriceForCurrency(mSelectedCurrency, this);
    }

    @Override
    public void onGetDefaultPriceResult(List<Price> prices) {
        mViewModel.defaultPrices.clear();
        mViewModel.defaultPrices.addAll(prices);
        mViewModel.prices.clear();
        mViewModel.prices.addAll(mViewModel.defaultPrices);
        mViewModel.isLoadingData.set(false);
    }

    @Override
    public void onGetDefaultPriceFailure() {
        mViewModel.isLoadingData.set(false);
        mViewModel.prices.clear();
        showSnackBar(getString(R.string.generic_error));
    }

    @Override
    public void onGetCurrencyPriceResult(Price price) {
        mViewModel.prices.clear();
        mViewModel.prices.add(price);
        mViewModel.prices.addAll(mViewModel.defaultPrices);
        mViewModel.isLoadingData.set(false);
    }

    @Override
    public void onGetCurrencyPriceFailure() {
        mViewModel.isLoadingData.set(false);
        mViewModel.prices.clear();
        showSnackBar(getString(R.string.generic_error));
    }


    @Override
    protected void onDestroy() {
        if (mDataSource != null)
            mDataSource.cancelCalls();
        super.onDestroy();
    }
}