package com.geekbrains.lesson1.clear.data.network;


import com.geekbrains.lesson1.clear.data.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("v2/top-headlines?sources=techcrunch&apiKey=6c0bf4b64c3c4755b9fe91879368b157")
    Call<ArticleResponse> getArticles();
}
