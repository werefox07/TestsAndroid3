package com.geekbrains.lesson7.clear.presentation.main;

import android.os.Bundle;

import com.geekbrains.lesson7.App;
import com.geekbrains.lesson7.R;
import com.geekbrains.lesson7.clear.di.AppComponent2;
import com.geekbrains.lesson7.clear.di.ClearModule;
import com.geekbrains.lesson7.clear.di.DaggerClearComponent;
import com.geekbrains.lesson7.clear.domain.usecase.ArticleInteractor;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClearActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private ClearViewModel viewModel;

    @Inject
    ArticleInteractor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initViewModel();
        initRecycleView();

        viewModel.articleLiveData.observe(this, data -> {
            adapter.setList(data);
        });
    }

    private void initViewModel() {
        AppComponent2 comp = ((App) getApplication()).getComponent();

        DaggerClearComponent.builder()
                .clearModule(new ClearModule())
                .appComponent2(comp)
                .build()
                .inject(this);

        viewModel = ViewModelProviders.of(this, new ClearViewModelFactory(interactor)).get(ClearViewModel.class);
        getLifecycle().addObserver(viewModel);
    }

    private void initRecycleView() {
        adapter = new ArticleAdapter();

        RecyclerView rv = findViewById(R.id.rv_articles);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}
