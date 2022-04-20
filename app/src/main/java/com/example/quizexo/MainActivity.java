package com.example.quizexo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuizDb database = Room.databaseBuilder(getApplicationContext(), QuizDb.class, "QuizDB").build();

        executor.execute(() -> {
            QuestionDao questionDao = database.questionDao();
            ChoiceDao choiceDao = database.choiceDao();

            Question question1 = new Question(0, "This is question 1");
            Question question2 = new Question(0, "This is question 2");

            Choice choice1 = new Choice(0, 1, "choice n°1 for question 1" );
            Choice choice2 = new Choice(0, 2, "choice n°1 for question 2" );


            choiceDao.save(choice1);
            choiceDao.save(choice2);

            questionDao.save(question1);
            questionDao.save(question2);


            List<QuestionChoices> l = questionDao.parseAll();
            for(QuestionChoices c : l)
                System.out.println("****************************************** " + c.question + " - " + c.choices);


        });

    }
}