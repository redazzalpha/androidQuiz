package com.example.quizexo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Question.class, Choice.class}, version = 1)
public abstract class QuizDb extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract ChoiceDao choiceDao();

}
