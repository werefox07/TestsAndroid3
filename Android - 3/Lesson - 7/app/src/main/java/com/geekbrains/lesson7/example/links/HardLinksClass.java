package com.geekbrains.lesson7.example.links;


import com.geekbrains.lesson7.example.engine.V4Engine;
import com.geekbrains.lesson7.example.engine.V6Engine;

public class HardLinksClass {

    private V4Engine engineV4;
    private V6Engine engineV6;

    public HardLinksClass() {
        engineV4 = new V4Engine();
        engineV6 = new V6Engine();
        engineV4.start();
        engineV6.start();
    }

    public static void main(String[] args){
        new HardLinksClass();
    }
}

