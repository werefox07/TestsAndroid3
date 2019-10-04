package com.geekbrains.lesson7.clear.di;

import com.geekbrains.lesson7.clear.presentation.main.ClearActivity;

import dagger.Component;

@Component(
        modules = ClearModule.class,
        dependencies = AppComponent2.class
)
@ActivityScope
public interface ClearComponent {


    void inject(ClearActivity activity);
}
