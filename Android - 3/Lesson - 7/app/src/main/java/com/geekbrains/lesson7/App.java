package com.geekbrains.lesson7;

import android.app.Application;

import com.geekbrains.lesson7.clear.di.AppComponent2;
import com.geekbrains.lesson7.clear.di.AppComponent2Module;
import com.geekbrains.lesson7.clear.di.ContextModule;
import com.geekbrains.lesson7.clear.di.DaggerAppComponent2;
import com.geekbrains.lesson7.clear.di.DataModule;

public class App extends Application {

    private AppComponent2 appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //appComponent = DaggerAppComponent.create();

        // когда добавляем модуль
        /*appComponent = DaggerAppComponent
                .builder()
                .autoDetailsModule(new AutoDetailsModule())
                .build();*/

        appComponent = DaggerAppComponent2
                .builder()
                .appComponent2Module(new AppComponent2Module())
                .contextModule(new ContextModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public AppComponent2 getComponent() {
        return appComponent;
    }
}
