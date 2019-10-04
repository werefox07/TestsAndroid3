package com.geekbrains.lesson7.example.di;

import com.geekbrains.lesson7.example.details.Chassis;
import com.geekbrains.lesson7.example.details.Wheels;

import dagger.Module;
import dagger.Provides;

@Module
public class AutoDetailsModule {

    Chassis chassis;
    Wheels wheels;

    public AutoDetailsModule() {

    }

    public AutoDetailsModule(Chassis chassis, Wheels wheels) {
        this.chassis = chassis;
        this.wheels = wheels;
    }

    @Provides
    Chassis provideChassis() {
        return new Chassis();
    }

    @Provides
    Wheels provideWheels(){
        return new Wheels();
    }
}
