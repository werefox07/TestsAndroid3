package com.geekbrains.lesson7.example.engine;


import javax.inject.Inject;

public class V6Engine implements Engine {

    @Inject
    public V6Engine() {
    }

    @Override
    public void start() {
        System.out.println("Start V6Engine");
    }
}
