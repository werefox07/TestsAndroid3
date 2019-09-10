package com.geekbrains.lesson1.clear.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article implements Comparable<Article> {

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

    @Override
    public int compareTo(Article o) {
        return publishedAt.compareTo(o.publishedAt);
    }
}
