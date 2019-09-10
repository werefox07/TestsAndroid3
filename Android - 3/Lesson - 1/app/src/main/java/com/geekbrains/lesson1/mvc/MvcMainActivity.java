package com.geekbrains.lesson1.mvc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import androidx.appcompat.app.AppCompatActivity;
import com.geekbrains.lesson1.R;

public class MvcMainActivity extends AppCompatActivity implements Observer, View.OnClickListener {

    private Model mModel;
    private Button btnCounter1;
    private Button btnCounter2;
    private Button btnCounter3;

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
        mModel = new Model();
        mModel.addObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCounter1:
                mModel.setElementValueAtIndex(0);
                break;
            case R.id.btnCounter2:
                mModel.setElementValueAtIndex(1);
                break;
            case R.id.btnCounter3:
                mModel.setElementValueAtIndex(2);
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        btnCounter1.setText("Количество = " + mModel.getElementValueAtIndex(0));
        btnCounter2.setText("Количество = " + mModel.getElementValueAtIndex(1));
        btnCounter3.setText("Количество = " + mModel.getElementValueAtIndex(2));
    }
}

