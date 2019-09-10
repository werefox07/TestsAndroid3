package com.geekbrains.lesson1.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {

    private List<Integer> mList;

    public Model() {
        mList = new ArrayList<>(3);
        mList.add(0);
        mList.add(0);
        mList.add(0);
    }

    public int getElementValueAtIndex(int _index) {
        return mList.get(_index);
    }

    public void setElementValueAtIndex(int _index) {
        int currentValue = mList.get(_index);
        mList.set(_index, currentValue + 1);
        setChanged();
        notifyObservers();
    }
}
