package com.geekbrains.ru.lesson8;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.geekbrains.ru.lesson8.data.models.RepsModel;
import com.geekbrains.ru.lesson8.data.rest.NetApiClient;
import com.geekbrains.ru.lesson8.presenter.RepsPresenter;
import com.geekbrains.ru.lesson8.presenter.RepsView;

import java.util.List;


public class MainActivity extends MvpAppCompatActivity
        implements RepsView {

    private ProgressBar progress;
    private View content;
    private View empty;

    @InjectPresenter(type = PresenterType.LOCAL)
    RepsPresenter repsPresenter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    RepsPresenter providePresenter() {
        return new RepsPresenter(NetApiClient.getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = findViewById(R.id.contentView);
        progress = findViewById(R.id.loadingView);
        empty = findViewById(R.id.emptyView);
    }

    @Override
    public void showLoading() {
        empty.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showRepoList(List<RepsModel> list) {
        empty.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyState() {
        empty.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
}
