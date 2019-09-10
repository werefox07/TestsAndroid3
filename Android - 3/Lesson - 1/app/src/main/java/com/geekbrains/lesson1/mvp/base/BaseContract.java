package com.geekbrains.lesson1.mvp.base;

public interface BaseContract {

    interface View {
    }

    interface Presenter<V extends BaseContract.View> {
        void attachView(V view);
        void detachView();
    }
}
