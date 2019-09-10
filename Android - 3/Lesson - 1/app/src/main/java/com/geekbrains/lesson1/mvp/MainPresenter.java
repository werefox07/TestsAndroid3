package com.geekbrains.lesson1.mvp;


import com.geekbrains.lesson1.mvp.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private Model mModel;

    public MainPresenter() {
        mModel = new Model();
    }

    @Override
    public void incSec() {
        int newModelValue = calcNewModelValue(0);
        mModel.setElementValueAtIndex(0, newModelValue);
        view.setSeconds(newModelValue);
    }

    @Override
    public void incMin() {
        int newModelValue = calcNewModelValue(1);
        mModel.setElementValueAtIndex(1, newModelValue);
        view.setMinute(newModelValue);
    }

    @Override
    public void incHours() {
        int newModelValue = calcNewModelValue(2);
        mModel.setElementValueAtIndex(2, newModelValue);
        view.setHours(newModelValue);
    }

    private int calcNewModelValue(int modelElementIndex) {
        int currentValue = mModel.getElementValueAtIndex(modelElementIndex);
        return currentValue + 1;
    }
}
