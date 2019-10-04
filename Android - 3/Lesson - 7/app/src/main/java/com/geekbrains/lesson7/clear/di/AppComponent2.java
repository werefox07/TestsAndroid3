package com.geekbrains.lesson7.clear.di;

import com.geekbrains.lesson7.clear.domain.repository.ArticleRepository;


import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppComponent2Module.class,
        ContextModule.class,
        DataModule.class
})
@Singleton
public interface AppComponent2 {

    ArticleRepository getRepository();
}
