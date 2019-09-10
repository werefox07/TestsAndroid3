package com.geekbrains.lesson1.mvvm;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MVVMViewModel extends ViewModel {

    private Model mModel;

    public MutableLiveData<Integer> seconds = new MutableLiveData<>();
    public MutableLiveData<Integer> minutes = new MutableLiveData<>();
    public MutableLiveData<Integer> hours = new MutableLiveData<>();

    public MVVMViewModel() {
        mModel = new Model();
    }

    public void incSec() {
        int newModelValue = calcNewModelValue(0);
        mModel.setElementValueAtIndex(0, newModelValue);
        seconds.setValue(newModelValue);
    }

    public void incMin() {
        int newModelValue = calcNewModelValue(1);
        mModel.setElementValueAtIndex(1, newModelValue);
        minutes.setValue(newModelValue);
    }

    public void incHours() {
        int newModelValue = calcNewModelValue(2);
        mModel.setElementValueAtIndex(2, newModelValue);
        hours.setValue(newModelValue);
    }

    private int calcNewModelValue(int modelElementIndex) {
        int currentValue = mModel.getElementValueAtIndex(modelElementIndex);
        return currentValue + 1;
    }
}
