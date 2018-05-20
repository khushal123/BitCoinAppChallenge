package com.purpletealabs.bitcoinapp.datasource;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory;
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService;
import com.purpletealabs.bitcoinapp.dtos.DefaultPriceListResponse;
import com.purpletealabs.bitcoinapp.dtos.Price;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.schedulers.Schedulers;

public class PriceDataSourceNetwork implements IPriceDataSource {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void cancel() {
        unSubscribeFromObservable();
        compositeDisposable = null;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


    @Override
    public void getDefaultPriceList(final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();
        Observable<DefaultPriceListResponse> call = service.getDefaultPriceList();
        Disposable disposable = call.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DefaultPriceListResponse>() {
            @Override
            public void accept(DefaultPriceListResponse defaultPriceListResponse) throws Exception {
                if (defaultPriceListResponse != null) {
                    List<Price> prices = new ArrayList<>();
                    prices.add(defaultPriceListResponse.getBpi().getUsdPrice());
                    prices.add(defaultPriceListResponse.getBpi().getGbpPrice());
                    prices.add(defaultPriceListResponse.getBpi().getEurPrice());
                    callback.onGetDefaultPriceResult(prices);

                } else {
                    callback.onGetDefaultPriceFailure();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onGetDefaultPriceFailure();
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getPriceForCurrency(final String currencyCode, final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();
        final Observable<ResponseBody> call = service.getPriceForCountry(currencyCode);
        Disposable disposable = call.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                if (responseBody != null) {
                    try {
                        String jsonResponse = responseBody.string();
                        JSONObject obj = new JSONObject(jsonResponse);
                        Price price = new Gson().fromJson(obj.getJSONObject("bpi").getString(currencyCode), Price.class);
                        callback.onGetCurrencyPriceResult(price);

                    } catch (IOException | JSONException e) {
                        callback.onGetCurrencyPriceFailure();
                    }
                } else {
                    callback.onGetCurrencyPriceFailure();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onGetCurrencyPriceFailure();
            }
        });
        compositeDisposable.add(disposable);
    }


}