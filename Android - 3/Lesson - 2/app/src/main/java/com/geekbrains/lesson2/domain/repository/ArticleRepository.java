package com.geekbrains.lesson2.domain.repository;


import com.geekbrains.lesson2.domain.model.Article;

import java.util.List;

import io.reactivex.Single;

public interface ArticleRepository {

    Single<List<Article>> getArticles();
}
