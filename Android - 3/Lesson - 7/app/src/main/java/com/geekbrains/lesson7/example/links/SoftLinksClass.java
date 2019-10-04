package com.geekbrains.lesson7.example.links;

import com.geekbrains.lesson7.example.engine.V4Engine;
import com.geekbrains.lesson7.example.engine.V6Engine;

public class SoftLinksClass {

    private V4Engine engineV4;
    private V6Engine engineV6;

    public SoftLinksClass(V4Engine engineV4, V6Engine engineV6) {
        this.engineV4 = engineV4;
        this.engineV6 = engineV6;


        this.engineV4.start();
        this.engineV6.start();
    }

    public static void main(String[] args){
        new SoftLinksClass(new V4Engine(), new V6Engine());
    }
}

