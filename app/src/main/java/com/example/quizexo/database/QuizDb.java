package com.example.quizexo.database;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.quizexo.dao.AnswerDao;
import com.example.quizexo.dao.QuestionDao;
import com.example.quizexo.dao.ChoiceDao;
import com.example.quizexo.dao.UserDao;
import com.example.quizexo.models.Answer;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.Question;
import com.example.quizexo.models.User;

@Database(entities = {Question.class, Choice.class, User.class, Answer.class}, version = 1)
public abstract class QuizDb extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract ChoiceDao choiceDao();
    public abstract UserDao userDao();
    public abstract AnswerDao answerDao();
}
