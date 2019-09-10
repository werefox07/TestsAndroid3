package com.geekbrains.lesson1.clear.domain.repository;


import com.geekbrains.lesson1.clear.domain.model.Article;

import java.util.List;

public interface ArticleRepository {

    List<Article> getArticles();
}
