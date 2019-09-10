package com.geekbrains.lesson1.clear.domain.usecase;

import com.geekbrains.lesson1.clear.domain.model.Article;
import com.geekbrains.lesson1.clear.domain.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleInteractor {

    private ArticleRepository repository;

    public ArticleInteractor(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> getArticles() {
        List<Article> articles = repository.getArticles();
        if(articles == null) return new ArrayList<Article>();
        Collections.sort(articles);
        return articles;
    }
}
