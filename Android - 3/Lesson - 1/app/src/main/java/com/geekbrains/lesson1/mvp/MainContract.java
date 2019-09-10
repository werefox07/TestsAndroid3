package com.geekbrains.lesson1.mvp;

import com.geekbrains.lesson1.mvp.base.BaseContract;

interface MainContract {
    interface View extends BaseContract.View {
        void setSeconds(int value);
        void setMinute(int value);
        void setHours(int value);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void incSec();
        void incMin();
        void incHours();
    }
}
