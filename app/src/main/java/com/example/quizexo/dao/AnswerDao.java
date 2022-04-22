package com.example.quizexo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizexo.models.Answer;
import com.example.quizexo.models.User;

import java.util.List;

@Dao
public interface AnswerDao {
    @Insert
    public void save(Answer answer);

    @Insert
    public void saveAll(Answer ...answers);

    @Update
    public void update(Answer answer);

    @Delete
    public void delete(Answer answer);

    @Query("select * from answer")
    public List<Answer> getAll();


}
