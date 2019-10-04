package com.geekbrains.lesson7.example.di;


import com.geekbrains.lesson7.example.Auto;
import com.geekbrains.lesson7.example.details.Chassis;
import com.geekbrains.lesson7.example.details.Wheels;
import com.geekbrains.lesson7.example.engine.V4Engine;
import com.geekbrains.lesson7.example.engine.V6Engine;

import dagger.Component;

@Component(modules = AutoDetailsModule.class)
public interface AppComponent {

    Auto getAuto();

    //V4Engine getV4engine();
    //V6Engine getV6engine();

    Wheels getWheels();
    Chassis getChassis();

    //void inject(MainActivity activity);
}
