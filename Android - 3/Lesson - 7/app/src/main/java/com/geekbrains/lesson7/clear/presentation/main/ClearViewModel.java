package com.geekbrains.lesson7.clear.presentation.main;


import com.geekbrains.lesson7.clear.domain.model.Article;
import com.geekbrains.lesson7.clear.domain.usecase.ArticleInteractor;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class ClearViewModel extends ViewModel implements LifecycleObserver {

    private ArticleInteractor interactor;

    public MutableLiveData<List<Article>> articleLiveData = new MutableLiveData<>();

    public ClearViewModel(ArticleInteractor interactor) {
        this.interactor = interactor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        new Thread() {
            @Override
            public void run() {
                List<Article> list = interactor.getArticles();
                articleLiveData.postValue(list);
            }
        }.start();
    }
}
