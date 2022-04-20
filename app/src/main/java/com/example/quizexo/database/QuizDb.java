package com.example.quizexo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.quizexo.dao.QuestionDao;
import com.example.quizexo.dao.ChoiceDao;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.Question;

@Database(entities = {Question.class, Choice.class}, version = 1)
public abstract class QuizDb extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract ChoiceDao choiceDao();

}
