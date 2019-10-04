package com.geekbrains.lesson7.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.geekbrains.lesson7.App;
import com.geekbrains.lesson7.R;
import com.geekbrains.lesson7.example.di.AppComponent;

public class MainActivity extends AppCompatActivity {

    //@Inject
    //Auto auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ручное внедрение зависимостей
        // V4Engine v4Engine = new V4Engine();
        // V6Engine v6Engine = new V6Engine();
        // Auto auto = new Auto(v4Engine, v6Engine);
        // auto.start();

        // когда Inject constructor
        //AppComponent comp = ((App)getApplication()).getComponent();
        //comp.getAuto().start();


        // ((App)getApplication()).inject(this);

        //comp.getChassis();
        //comp.getWheels();
    }
}
