package com.geekbrains.lesson6.data.entity;

import com.geekbrains.lesson6.domain.model.Article;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticleRoomData {

    @ColumnInfo(name = "author")
    public String author;

    public String title;
    public String description;
    public String url;
    public String urlToImage;

    @PrimaryKey
    @NonNull
    public String publishedAt;

    public String content;

    public ArticleRoomData() {
    }

    @Ignore
    public ArticleRoomData(String author, String title, String description, String url, String urlToImage, @NonNull String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    @NonNull
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(@NonNull String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Article convertToEntity(ArticleRoomData item) {
        return new Article(
                item.author,
                item.title,
                item.description,
                item.url,
                item.urlToImage,
                item.publishedAt,
                item.content);
    }

    public static ArticleRoomData convertToRoomData(ArticleData item) {
        return new ArticleRoomData(
                item.author,
                item.title,
                item.description,
                item.url,
                item.urlToImage,
                item.publishedAt,
                item.content);
    }
}
