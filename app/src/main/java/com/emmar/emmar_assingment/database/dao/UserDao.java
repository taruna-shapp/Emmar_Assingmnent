package com.emmar.emmar_assingment.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.emmar.emmar_assingment.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> user);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();
}