package com.geekbrains.lesson7.clear.di;

import com.geekbrains.lesson7.clear.data.network.Api;
import com.geekbrains.lesson7.clear.data.repository.ArticleRepositoryImpl;
import com.geekbrains.lesson7.clear.domain.repository.ArticleRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {


    @Provides
    @Singleton
    ArticleRepository provideRepository(Api api) {
        return new ArticleRepositoryImpl(api);
    }
}
