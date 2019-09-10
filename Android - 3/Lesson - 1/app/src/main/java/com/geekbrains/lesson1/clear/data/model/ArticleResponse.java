package com.geekbrains.lesson1.clear.data.model;

import com.geekbrains.lesson1.clear.domain.model.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("totalResult")
    @Expose
    public Integer totalResult;

    @SerializedName("articles")
    @Expose
    public List<Article> articles;
}
