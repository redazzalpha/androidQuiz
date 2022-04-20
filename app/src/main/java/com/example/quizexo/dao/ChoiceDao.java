package com.example.quizexo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizexo.models.Choice;

import java.util.List;

@Dao
public interface ChoiceDao {
    @Insert
    public void save(Choice choice);

    @Insert
    public void saveAll(Choice ...choices);

    @Update
    public void update(Choice choice);

    @Delete
    public void delete(Choice choice);

    @Query("select * from choice")
    public List<Choice> getAll();

}
