package com.geekbrains.ru.lesson8.presenter;


import com.arellomobile.mvp.MvpView;
import com.geekbrains.ru.lesson8.data.models.RepsModel;

import java.util.List;


public interface RepsView extends MvpView {

    void showError(Throwable e);

    void showLoading();

    void hideLoading();

    void showRepoList(List<RepsModel> list);

    void showEmptyState();
}
