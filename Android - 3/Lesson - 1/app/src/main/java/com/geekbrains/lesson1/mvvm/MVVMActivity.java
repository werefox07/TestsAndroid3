package com.geekbrains.lesson1.mvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.geekbrains.lesson1.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MVVMActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCounter1;
    private Button btnCounter2;
    private Button btnCounter3;

    private MVVMViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCounter1 = (Button) findViewById(R.id.btnCounter1);
        btnCounter2 = (Button) findViewById(R.id.btnCounter2);
        btnCounter3 = (Button) findViewById(R.id.btnCounter3);
        btnCounter1.setOnClickListener(this);
        btnCounter2.setOnClickListener(this);
        btnCounter3.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this).get(MVVMViewModel.class);

        viewModel.seconds.observe(this, data -> {
            btnCounter1.setText("Количество = " + data);
        });
        viewModel.minutes.observe(this, data -> {
            btnCounter2.setText("Количество = " + data);
        });
        viewModel.hours.observe(this, data -> {
            btnCounter3.setText("Количество = " + data);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCounter1: {
                viewModel.incSec();
                break;
            }
            case R.id.btnCounter2: {
                viewModel.incMin();
                break;
            }
            case R.id.btnCounter3:{
                viewModel.incHours();
                break;
            }
        }
    }
}
