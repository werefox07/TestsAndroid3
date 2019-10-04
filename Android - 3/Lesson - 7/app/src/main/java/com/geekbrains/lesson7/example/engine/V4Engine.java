package com.geekbrains.lesson7.example.engine;


import javax.inject.Inject;

public class V4Engine implements Engine {

    @Inject
    public V4Engine() {
    }

    @Override
    public void start() {
        System.out.println("Start V4Engine");
    }
}
