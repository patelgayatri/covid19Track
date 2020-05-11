package com.health.covid19Track.repositories;


import androidx.lifecycle.MutableLiveData;

import com.health.covid19Track.model.Country;
import com.health.covid19Track.network.RequestInterface;
import com.health.covid19Track.network.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.health.covid19Track.utils.ConstantsKt.BASE_URL_Country;

public class CasesRepository {

    private static CasesRepository casesRepository;
    MutableLiveData<Country> caseData = new MutableLiveData<>();


    public static CasesRepository getInstance() {
        if (casesRepository == null) {
            casesRepository = new CasesRepository();
        }
        return casesRepository;
    }

    private RequestInterface requestAPI;

    public CasesRepository() {
        RetrofitService.RetrofitBuilder(BASE_URL_Country);
        requestAPI = RetrofitService.cteateService(RequestInterface.class);
    }

    public MutableLiveData<Country> getCases() {
        Observable<Country> observableCase = requestAPI.getCountryStats();

        observableCase
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Country>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Country country) {
                        caseData.setValue(country);
                    }

                    @Override
                    public void onError(Throwable e) {
                        caseData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return caseData;
    }

}


