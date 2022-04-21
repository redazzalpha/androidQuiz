package com.example.quizexo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.quizexo.dao.ChoiceDao;
import com.example.quizexo.dao.QuestionDao;
import com.example.quizexo.database.QuizDb;
import com.example.quizexo.defines.Defines;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.Question;
import com.example.quizexo.models.QuestionChoices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor.execute(this::createDatabase);

    }

    public void createDatabase() {
        // get database instance
        QuizDb database = Room.databaseBuilder(getApplicationContext(), QuizDb.class, "QuizDB").build();
        database.clearAllTables();

        // get Dao
        QuestionDao questionDao = database.questionDao();
        ChoiceDao choiceDao = database.choiceDao();

        int i = 1, j = 1;

        // save questions over database
        List<Question> questions = new ArrayList<>();
        for(String question : Defines.questions)
            questions.add(new Question(i++, question));

        for(Question question : questions)
            questionDao.save(question);

        // save choices over database
        List<Choice> choices = new ArrayList<>();

        i = 1;
        for(String choice : Defines.choices){
            if(!choice.equals("\n"))
                choices.add(new Choice(j++, i, choice));
            else i++;
        }

        for(Choice choice : choices)
            choiceDao.save(choice);

        // show results
        List<QuestionChoices> l = questionDao.getAllQuestionChoices();
        for(QuestionChoices c : l)
            System.out.println("****************************************** " + c.question + " - " + c.choices);
    }
}