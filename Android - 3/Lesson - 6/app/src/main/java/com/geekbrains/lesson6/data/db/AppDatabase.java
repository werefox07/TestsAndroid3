package com.geekbrains.lesson6.data.db;

import android.content.Context;

import com.geekbrains.lesson6.data.entity.ArticleRoomData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {ArticleRoomData.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "Sample.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    abstract ArticleDao articleDao();
}