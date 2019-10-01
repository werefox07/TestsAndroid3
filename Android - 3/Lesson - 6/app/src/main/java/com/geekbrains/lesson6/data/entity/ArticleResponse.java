package com.geekbrains.lesson6.data.entity;

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
    public List<ArticleData> articles;
}
