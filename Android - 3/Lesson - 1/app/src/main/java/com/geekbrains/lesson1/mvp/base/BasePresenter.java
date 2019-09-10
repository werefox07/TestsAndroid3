package com.geekbrains.lesson1.mvp.base;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    protected V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
