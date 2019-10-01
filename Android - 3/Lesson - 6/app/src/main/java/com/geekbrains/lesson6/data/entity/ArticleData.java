package com.geekbrains.lesson6.data.entity;

import com.geekbrains.lesson6.domain.model.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleData {

    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("urlToImage")
    @Expose
    public String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;

    @SerializedName("content")
    @Expose
    public String content;

    public static Article convertToEntity(ArticleData item) {
        return new Article(
                item.author,
                item.title,
                item.description,
                item.url,
                item.urlToImage,
                item.publishedAt,
                item.content);
    }
}
