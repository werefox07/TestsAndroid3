package com.geekbrains.ru.lesson8.data.rest;


import com.geekbrains.ru.lesson8.data.models.RepsModel;

import java.util.List;

import io.reactivex.Single;


public interface NetApiClientInterface {
    Single<List<RepsModel>> getReps();
}
