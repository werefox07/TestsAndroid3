package com.geekbrains.lesson7.example;

import com.geekbrains.lesson7.example.engine.V4Engine;
import com.geekbrains.lesson7.example.engine.V6Engine;

import javax.inject.Inject;

public class Auto {

    private V4Engine v4Engine;
    private V6Engine v6Engine;

    @Inject
    public Auto(V4Engine v4Engine, V6Engine v6Engine) {
        this.v4Engine = v4Engine;
        this.v6Engine = v6Engine;
    }

    public void start(){
        v4Engine.start();
        v6Engine.start();
    }
}
