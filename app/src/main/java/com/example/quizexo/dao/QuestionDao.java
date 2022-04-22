package com.example.quizexo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.quizexo.models.Question;
import com.example.quizexo.models.QuestionChoices;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    public void save(Question question);

    @Insert
    public void saveAll(Question ...questions);

    @Update
    public void update(Question question);

    @Delete
    public void delete(Question question);

    @Query("select * from  question where  questionId = :id")
    public Question getById(long id);

    @Query("select * from question")
    public List<Question> getAll();

    @Query("select * from question")
    public List<QuestionChoices> getAllQuestionChoices();



}
