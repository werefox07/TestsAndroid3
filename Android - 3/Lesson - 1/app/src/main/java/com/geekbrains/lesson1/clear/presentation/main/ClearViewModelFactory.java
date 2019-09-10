package com.geekbrains.lesson1.clear.presentation.main;

import com.geekbrains.lesson1.clear.domain.usecase.ArticleInteractor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ClearViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private ArticleInteractor interactor;

    public ClearViewModelFactory(ArticleInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ClearViewModel.class) {
            return (T) new ClearViewModel(interactor);
        }
        return null;
    }
}
