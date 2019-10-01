package com.geekbrains.lesson6.domain.repository;


import com.geekbrains.lesson6.domain.model.Article;

import java.util.List;

import io.reactivex.Single;

public interface ArticleRepository {

    Single<List<Article>> getArticles();
}
