package com.purpletealabs.bitcoinapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.purpletealabs.bitcoinapp.R;
import com.purpletealabs.bitcoinapp.adapters.CountryListAdapter;
import com.purpletealabs.bitcoinapp.databinding.ActivitySelectCountryBinding;
import com.purpletealabs.bitcoinapp.datasource.CurrencyDataSourceNetwork;
import com.purpletealabs.bitcoinapp.datasource.ICurrencyDataSource;
import com.purpletealabs.bitcoinapp.dtos.Currency;
import com.purpletealabs.bitcoinapp.viewmodels.SelectCountryViewModel;

import java.util.List;

public class SelectCountryActivity extends BaseActivity implements ICurrencyDataSource.Callback, CountryListAdapter.ClickListener {
    public static final String EXTRA_SELECTED_CURRENCY = "currency";

    private SelectCountryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySelectCountryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_country);

        mViewModel = ViewModelProviders.of(this).get(SelectCountryViewModel.class);

        binding.setVm(mViewModel);

        setupToolbar();

        initViews(binding);

        getCountryList();
    }

    private void getCountryList() {
        mViewModel.isLoadingData.postValue(true);
        ICurrencyDataSource ds = new CurrencyDataSourceNetwork();
        ds.getSupportedCurrencies(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews(ActivitySelectCountryBinding binding) {
        binding.rvCountryList.setLayoutManager(new LinearLayoutManager(this));
        CountryListAdapter adapter = new CountryListAdapter(mViewModel.countries, this);
        binding.rvCountryList.setAdapter(adapter);
    }


    @Override
    public void getCurrenciesFailure() {
        mViewModel.isLoadingData.postValue(false);
        mViewModel.countries.clear();
    }

    @Override
    public void getCurrenciesResult(List<Currency> countries) {
        mViewModel.isLoadingData.postValue(false);
        mViewModel.countries.clear();
        mViewModel.countries.addAll(countries);
    }

    @Override
    public void onCountrySelected(Currency currency) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SELECTED_CURRENCY, currency.getCurrency());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}