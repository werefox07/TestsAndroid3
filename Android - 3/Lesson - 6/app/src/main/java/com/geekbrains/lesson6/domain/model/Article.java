package com.geekbrains.lesson6.domain.model;

public class Article implements Comparable<Article> {

    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;

    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    @Override
    public int compareTo(Article o) {
        return publishedAt.compareTo(o.publishedAt);
    }
}
