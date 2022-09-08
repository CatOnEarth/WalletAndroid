package com.snail.wallet.MainScreen.db.CategoryDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.snail.wallet.MainScreen.models.Category;

import java.util.List;


@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}