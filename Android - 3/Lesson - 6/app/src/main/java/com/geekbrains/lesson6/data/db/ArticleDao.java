package com.geekbrains.lesson6.data.db;

import com.geekbrains.lesson6.data.entity.ArticleRoomData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
abstract class ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long insertArticle(ArticleRoomData user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long[] insertArticles(List<ArticleRoomData> user);

    // Update ищет в бд запись по ключу. Если не найдет, то ничего не произойдет.
    // Если найдет, то обновит все поля, а не только те, которые мы заполнили в Entity объекте.
    // В ответ получаем кол-во обновленных записей
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract int updateArticle(ArticleRoomData user);

    // Так же ищет данные по ключу и удаляет
    // В ответ получаем кол-во удаленных записей
    @Delete
    abstract void deleteArticle(ArticleRoomData user);

    @Query("SELECT * FROM articles")
    abstract List<ArticleRoomData> getArticles();

    @Query("SELECT * FROM articles WHERE publishedAt = :time")
    abstract List<ArticleRoomData> getArticle(String time);

    @Query("UPDATE articles SET author = :author WHERE publishedAt = :time")
    abstract int updateArticleCustom(String author, String time);
}