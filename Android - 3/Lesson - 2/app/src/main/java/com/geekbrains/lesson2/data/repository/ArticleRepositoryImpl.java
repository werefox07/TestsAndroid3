package com.geekbrains.lesson2.data.repository;

import com.geekbrains.lesson2.data.network.Api;
import com.geekbrains.lesson2.domain.model.Article;
import com.geekbrains.lesson2.domain.repository.ArticleRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ArticleRepositoryImpl implements ArticleRepository {

    private Api api;

    public ArticleRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public Single<List<Article>> getArticles() {
        return api.getCrypto()
                .subscribeOn(Schedulers.io())
                .map(response -> response.articles);
    }
}
