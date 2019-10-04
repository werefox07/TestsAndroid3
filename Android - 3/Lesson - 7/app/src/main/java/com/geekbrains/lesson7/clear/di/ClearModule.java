package com.geekbrains.lesson7.clear.di;

import com.geekbrains.lesson7.clear.domain.repository.ArticleRepository;
import com.geekbrains.lesson7.clear.domain.usecase.ArticleInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class ClearModule {

    @Provides
    @ActivityScope
    ArticleInteractor provideInteractor(ArticleRepository repository) {
        return new ArticleInteractor(repository);
    }
}
