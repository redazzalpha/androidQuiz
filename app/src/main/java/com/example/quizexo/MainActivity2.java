package com.example.quizexo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizexo.dao.AnswerDao;
import com.example.quizexo.dao.UserDao;
import com.example.quizexo.defines.Defines;
import com.example.quizexo.models.Answer;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.QuestionChoices;
import com.example.quizexo.models.User;
import com.example.quizexo.models.UserAnswers;
import com.example.quizexo.utils.Dialog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity2 extends AppCompatActivity {
    static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private  TextView titleActivity2;
    private  TextView questionText;
    private LinearLayout wrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.titleActivity2 = findViewById(R.id.titleActivity2);
        this.questionText = findViewById(R.id.questionText);
        this.wrapper = findViewById(R.id.choiceWapper);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.initChoices();
    }

    public void initChoices() {
        QuestionChoices questionChoices;
        if ((MainActivity.questionChoicesIterator.hasNext())) {
            questionChoices = MainActivity.questionChoicesIterator.next();
            questionText.setText(questionChoices.question.questionText);
            wrapper.removeAllViews();
            String v = "Question " + MainActivity.questionCount++ + "/4";
            this.titleActivity2.setText(v);
            for (Choice choice : questionChoices.choices)
                this.createChoice(choice);
        }
        else this.setScore();

    }
    public void createChoice(Choice choice) {
        Button btnField = new Button(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Defines.BTN_MARGINS_LF, Defines.BTN_MARGINS_TB, Defines.BTN_MARGINS_LF, Defines.BTN_MARGINS_TB);

//        btnField.setId(Integer.parseInt("123"));
        btnField.setLayoutParams(params);
        btnField.setText(choice.choiceText);
        btnField.setBackgroundColor(Color.parseColor("#95c500"));
        btnField.setOnClickListener(this::saveAnswer);

        wrapper.addView(btnField);
    }
    public void saveAnswer(View view) {
        executor.execute(() -> {
            String answerStr = ((Button)view).getText().toString();

            UserDao userDao = MainActivity.userDao;
            User user = userDao.getByUsername(MainActivity.currentUser);

            AnswerDao answerDao = MainActivity.database.answerDao();
            Answer answer  = new Answer(0, user.id, answerStr);
            answerDao.save(answer);

        });
        this.initChoices();
    }
    public void setScore() {
        executor.execute(() -> {
            UserAnswers userAnswers = MainActivity.userDao.getUserAnswer(MainActivity.currentUser);
            int i = 0;
            for(Answer answer : userAnswers.answers) {
                if(answer.text.equals(Defines.ANSWERS[i++]))
                    userAnswers.user.score++;
            }
        });
        Dialog dialog = new Dialog(MainActivity2.this, "Quiz finished !", "Your score");
        dialog.show();
    }
}