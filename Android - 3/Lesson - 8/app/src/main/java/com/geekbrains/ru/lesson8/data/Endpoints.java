package com.geekbrains.ru.lesson8.data;


import com.geekbrains.ru.lesson8.data.models.RepsModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Endpoints {

    @GET("/repositories")
    Single<List<RepsModel>> getRepos();
}
