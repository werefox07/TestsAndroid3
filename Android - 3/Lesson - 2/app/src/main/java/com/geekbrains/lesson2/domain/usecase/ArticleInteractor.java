package com.geekbrains.lesson2.domain.usecase;


import com.geekbrains.lesson2.domain.model.Article;
import com.geekbrains.lesson2.domain.repository.ArticleRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class ArticleInteractor {

    private ArticleRepository repository;

    public ArticleInteractor(ArticleRepository repository) {
        this.repository = repository;
    }

    public Single<List<Article>> getArticles() {
        return repository.getArticles()
                .map(list -> {
                    Collections.sort(list);
                    return list;
                } );
    }
}
