package com.geekbrains.lesson1.clear.data.repository;

import com.geekbrains.lesson1.clear.data.model.ArticleResponse;
import com.geekbrains.lesson1.clear.data.network.Api;
import com.geekbrains.lesson1.clear.domain.model.Article;
import com.geekbrains.lesson1.clear.domain.repository.ArticleRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ArticleRepositoryImpl implements ArticleRepository {

    private Api api;

    public ArticleRepositoryImpl(Api api) {
        this.api = api;
    }

    @Override
    public List<Article> getArticles() {
        Response<ArticleResponse> response;
        try {
            response = api.getArticles().execute();
        } catch (IOException e) {
            return null;
        }
        if (response.code() == 200) {
            ArticleResponse body = response.body();
            return body.articles;
        } else {
            return null;
        }
    }
}
