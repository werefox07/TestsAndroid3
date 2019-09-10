package com.geekbrains.lesson1.mosby;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface MosbyExampleView extends MvpView {
    void setButtonText(int btnIndex, int value);
}
