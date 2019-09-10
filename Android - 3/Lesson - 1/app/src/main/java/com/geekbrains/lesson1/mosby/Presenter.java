package com.geekbrains.lesson1.mosby;

import com.geekbrains.lesson1.R;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import androidx.annotation.NonNull;

public class Presenter extends MvpBasePresenter<MosbyExampleView> {

    private Model mModel;

    Presenter() {
        this.mModel = new Model();
    }

    private int calcNewModelValue(int modelElementIndex) {
        int currentValue = mModel.getElementValueAtIndex(modelElementIndex);
        return currentValue + 1;
    }

    public void buttonClick(final int btnIndex) {
        ifViewAttached(new ViewAction<MosbyExampleView>() {
            @Override
            public void run(@NonNull MosbyExampleView view) {
                int newModelValue;
                switch (btnIndex) {
                    case R.id.btnCounter1:
                        newModelValue = calcNewModelValue(0);
                        mModel.setElementValueAtIndex(0, newModelValue);
                        view.setButtonText(1, newModelValue);
                        break;
                    case R.id.btnCounter2:
                        newModelValue = calcNewModelValue(1);
                        mModel.setElementValueAtIndex(1, newModelValue);
                        view.setButtonText(2, newModelValue);
                        break;
                    case R.id.btnCounter3:
                        newModelValue = calcNewModelValue(2);
                        mModel.setElementValueAtIndex(2, newModelValue);
                        view.setButtonText(3, newModelValue);
                        break;
                }
            }
        });

    }
}
