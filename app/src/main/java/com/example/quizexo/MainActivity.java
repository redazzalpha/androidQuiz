package com.example.quizexo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.quizexo.dao.AnswerDao;
import com.example.quizexo.dao.ChoiceDao;
import com.example.quizexo.dao.QuestionDao;
import com.example.quizexo.dao.UserDao;
import com.example.quizexo.database.QuizDb;
import com.example.quizexo.defines.Defines;
import com.example.quizexo.models.Answer;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.Question;
import com.example.quizexo.models.QuestionChoices;
import com.example.quizexo.models.User;
import com.example.quizexo.models.UserAnswers;
import com.example.quizexo.utils.Dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    // properties
    static final ExecutorService executor = Executors.newSingleThreadExecutor();
    static QuizDb database;
    static UserDao  userDao;
    static QuestionDao questionDao;
    static ChoiceDao choiceDao;
    static Iterator<QuestionChoices> questionChoicesIterator;
    static Iterator<UserAnswers> userAnswersIterator;
    static String currentUser;

    private EditText inputName;
    private Button btnStart;
    private ImageButton btnDelete;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get components
        inputName = findViewById(R.id.inputName);
        btnStart = findViewById(R.id.btnStart);
        btnDelete = findViewById(R.id.btnDelete);

        // set listeners
        inputName.addTextChangedListener(new inputNameOnValueChange());
        btnDelete.setOnClickListener(this::clearInputName);
        btnStart.setOnClickListener(this::startQuiz);

        // create database
        executor.execute(this::createDatabase);

        disableButtons();
    }

    public void createDatabase() {
        setDatabase();
        setDao();
        saveQuestions();
        saveChoices();
        questionChoicesIterator = questionDao.getAllQuestionChoices().iterator();
    }
    public void createUser() {
        currentUser = inputName.getText().toString();
        userDao.save(new User(0, currentUser, 0));
        userAnswersIterator = userDao.getAllUserAnswer().iterator();
    }

    public void startQuiz(View view) {
        executor.execute(this::createUser);
        startActivity(new Intent(this, MainActivity2.class));
    }
    public void clearInputName(View view) {
        this.inputName.setText("");
    }

    public void enableStart() {
        btnStart.setEnabled(true);
        btnStart.setBackgroundColor(Color.parseColor("#95c500"));
    }
    public void enableDelete() {
        btnDelete.setEnabled(true);
        btnDelete.setColorFilter(Color.parseColor("#e90813"));
    }
    public void enableButtons() {
        this.enableStart();
        this.enableDelete();
    }

    public void disableStart() {
        btnStart.setEnabled(false);
        btnStart.setBackgroundColor(Color.GRAY);
    }
    public void disableDelete() {
        btnDelete.setEnabled(false);
        btnDelete.setColorFilter(Color.GRAY);
    }
    public void disableButtons() {
        this.disableStart();
        this.disableDelete();
    }

    private void setDatabase() {
        database = Room.databaseBuilder(getApplicationContext(), QuizDb.class, "QuizDB").build();
        database.clearAllTables();
    }
    private void setDao() {
        questionDao = database.questionDao();
        choiceDao = database.choiceDao();
        userDao = database.userDao();
    }
    private void saveQuestions() {
        List<Question> questions = new ArrayList<>();
        int i = 1, j = 1;
        for(String question : Defines.QUESTIONS)
            questions.add(new Question(i++, question));
        for(Question question : questions)
            questionDao.save(question);
    }
    private void saveChoices() {
        // save choices over database
        List<Choice> choices = new ArrayList<>();
        int i = 1, j = 1;
        for(String choice : Defines.CHOICES){
            if(!choice.equals("\n")) choices.add(new Choice(j++, i, choice));
            else i++;
        }
        for(Choice choice : choices)
            choiceDao.save(choice);
    }

    class inputNameOnValueChange implements TextWatcher {
        @Override
        public void afterTextChanged(Editable editable) {
            String username = editable.toString().trim();
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s]+(-[a-zA-Z0-9\\s]+)*$");
            Matcher matcher = pattern.matcher(username);
            boolean empty = username.isEmpty();

            if(!empty && matcher.find()) enableButtons();
            else if(empty) disableButtons();
            else disableStart();
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }
}