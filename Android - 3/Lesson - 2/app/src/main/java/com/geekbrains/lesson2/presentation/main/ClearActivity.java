package com.geekbrains.lesson2.presentation.main;

import android.os.Bundle;

import com.geekbrains.lesson2.R;
import com.geekbrains.lesson2.data.network.Api;
import com.geekbrains.lesson2.data.network.RetrofitInit;
import com.geekbrains.lesson2.data.repository.ArticleRepositoryImpl;
import com.geekbrains.lesson2.domain.repository.ArticleRepository;
import com.geekbrains.lesson2.domain.usecase.ArticleInteractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClearActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private ClearViewModel viewModel;

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
        Api api = RetrofitInit.newApiInstance();
        ArticleRepository repository = new ArticleRepositoryImpl(api);
        ArticleInteractor interactor = new ArticleInteractor(repository);

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
