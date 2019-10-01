package com.geekbrains.lesson6.data.db;

public interface DbProvider<T, R> {
    void insert(T data);

    void update(T data);

    void delete(T data);

    R select();
}
