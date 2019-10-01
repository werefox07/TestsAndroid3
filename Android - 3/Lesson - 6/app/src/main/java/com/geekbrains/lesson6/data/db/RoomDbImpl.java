package com.geekbrains.lesson6.data.db;

import android.os.Build;

import com.geekbrains.lesson6.data.entity.ArticleRoomData;
import com.geekbrains.lesson6.domain.model.Article;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;

public class RoomDbImpl implements DbProvider<ArticleRoomData, List<Article>> {

    private AppDatabase db;

    public RoomDbImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void insert(ArticleRoomData data) {
        db.articleDao().insertArticle(data);
    }

    @Override
    public void update(ArticleRoomData data) {
        db.runInTransaction(() -> {
            db.articleDao().updateArticle(data);
        });
    }

    @Override
    public void delete(ArticleRoomData data) {
        db.runInTransaction(() -> {
            db.articleDao().deleteArticle(data);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Article> select() {
        return db.articleDao().getArticles().stream()
                .map(item -> ArticleRoomData.convertToEntity(item))
                .collect(Collectors.toList());
    }
}
