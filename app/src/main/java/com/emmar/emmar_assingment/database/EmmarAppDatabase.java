package com.emmar.emmar_assingment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emmar.emmar_assingment.database.dao.UserDao;
import com.emmar.emmar_assingment.database.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class EmmarAppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "UserDatabase";

    public abstract UserDao userDao();

    private static volatile EmmarAppDatabase INSTANCE;

    public static EmmarAppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EmmarAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, EmmarAppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}