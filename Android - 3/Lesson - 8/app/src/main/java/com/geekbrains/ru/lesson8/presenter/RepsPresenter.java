package com.geekbrains.ru.lesson8.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.geekbrains.ru.lesson8.data.models.RepsModel;
import com.geekbrains.ru.lesson8.data.rest.NetApiClientInterface;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


@InjectViewState
public class RepsPresenter extends MvpPresenter<RepsView> {

    private NetApiClientInterface client;

    public RepsPresenter(NetApiClientInterface client) {
        this.client = client;
    }

    @Override
    public void attachView(RepsView view) {
        super.attachView(view);
        loadData();
    }

    private void loadData() {
        getViewState().showLoading();
        client.getReps().subscribe(new SingleObserver<List<RepsModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<RepsModel> repsModels) {
                if (repsModels.isEmpty()) {
                    getViewState().showEmptyState();
                } else {
                    getViewState().showRepoList(repsModels);
                }
                getViewState().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getViewState().showError(t);
                getViewState().hideLoading();
            }
        });
    }
}
