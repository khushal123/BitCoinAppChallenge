package com.purpletealabs.bitcoinapp.datasource;

import android.support.annotation.NonNull;

import com.purpletealabs.bitcoinapp.apis.CoinDeskServiceFactory;
import com.purpletealabs.bitcoinapp.apis.ICoinDeskService;
import com.purpletealabs.bitcoinapp.dtos.Currency;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class CurrencyDataSourceNetwork implements ICurrencyDataSource {

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
    public void getSupportedCurrencies(final Callback callback) {
        ICoinDeskService service = CoinDeskServiceFactory.newServiceInstance();
        Observable<List<Currency>> call = service.getSupportedCurrencies();
        Disposable disposable = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Currency>>() {
                               @Override
                               public void accept(List<Currency> currencies) throws Exception {
                                   if (currencies != null) {
                                       callback.getCurrenciesResult(currencies);
                                   } else {
                                       callback.getCurrenciesFailure();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                callback.getCurrenciesFailure();
                            }
                        });
        compositeDisposable.add(disposable);
    }


}
