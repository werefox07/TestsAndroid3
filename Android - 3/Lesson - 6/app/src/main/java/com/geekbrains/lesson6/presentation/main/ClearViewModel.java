package com.geekbrains.lesson6.presentation.main;


import com.geekbrains.lesson6.domain.model.Article;
import com.geekbrains.lesson6.domain.usecase.ArticleInteractor;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class ClearViewModel extends ViewModel implements LifecycleObserver {

    private ArticleInteractor interactor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<Article>> articleLiveData = new MutableLiveData<>();

    public ClearViewModel(ArticleInteractor interactor) {
        this.interactor = interactor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        compositeDisposable.add(
                interactor.getArticles()
                        .subscribe(result -> articleLiveData.postValue(result),
                                throwable -> System.out.println(throwable)
                        ));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
