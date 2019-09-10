package com.geekbrains.lesson1.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.geekbrains.lesson1.R;

public class MvpMainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private Button btnCounter1;
    private Button btnCounter2;
    private Button btnCounter3;
    private MainPresenter mPresenter;

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

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCounter1: {
                mPresenter.incSec();
                break;
            }
            case R.id.btnCounter2: {
                mPresenter.incMin();
                break;
            }
            case R.id.btnCounter3:{
                mPresenter.incHours();
                break;
            }
        }
    }

    @Override
    public void setSeconds(int value) {
        btnCounter1.setText("Количество = " + value);
    }

    @Override
    public void setMinute(int value) {
        btnCounter2.setText("Количество = " + value);
    }

    @Override
    public void setHours(int value) {
        btnCounter3.setText("Количество = " + value);
    }
}
