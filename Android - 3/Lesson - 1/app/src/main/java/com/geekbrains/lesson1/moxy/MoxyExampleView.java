package com.geekbrains.lesson1.moxy;

import com.arellomobile.mvp.MvpView;

public interface MoxyExampleView extends MvpView {
    void setButtonText(int btnIndex, int value);
}
