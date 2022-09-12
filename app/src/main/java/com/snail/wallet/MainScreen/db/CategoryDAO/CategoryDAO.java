package com.snail.wallet.MainScreen.db.CategoryDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.snail.wallet.MainScreen.models.parametrs.Category;

import java.util.List;


@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE id = :id")
    Category getCategoryById(long id);

    @Query("SELECT * FROM category WHERE name = :name")
    List<Category> getCategoryName(String name);

    @Query("SELECT * FROM category WHERE type = :type")
    List<Category> getCategoryByType(int type);

    @Query("SELECT name FROM category WHERE type = :type AND name = :name")
    List<String> getByNameAndTypeCategory(int type, String name);

    @Insert
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}