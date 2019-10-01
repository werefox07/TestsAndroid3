package com.geekbrains.lesson6.data.db;

import android.os.Build;

import com.geekbrains.lesson6.data.entity.ArticleRealmData;
import com.geekbrains.lesson6.domain.model.Article;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.RequiresApi;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDbImpl implements DbProvider<ArticleRealmData, List<Article>> {

    @Override
    public void insert(ArticleRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(data);
            realm.commitTransaction();
        }
    }

    @Override
    public void update(ArticleRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans -> {
                realm.copyToRealmOrUpdate(data);
            });
        }
    }

    @Override
    public void delete(ArticleRealmData data) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(trans -> {
                realm.where(ArticleRealmData.class).contains("..", data.getAuthor()).findAll().deleteAllFromRealm();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Article> select() {
        try (Realm realm = Realm.getDefaultInstance()) {
            final RealmResults<ArticleRealmData> results =
                    realm.where(ArticleRealmData.class)
                            .findAll();
            List<ArticleRealmData> items = realm.copyFromRealm(results);
            return items.stream()
                    .map(item -> ArticleRealmData.convertToEntity(item))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            return null;
        }
    }
}
