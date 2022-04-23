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
import com.example.quizexo.defines.Defines;
import com.example.quizexo.models.Answer;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.QuestionChoices;
import com.example.quizexo.models.User;
import com.example.quizexo.models.UserAnswers;
import com.example.quizexo.utils.Dialog;

import java.util.concurrent.Future;

import lombok.SneakyThrows;

public class MainActivity2 extends AppCompatActivity {
    // properties
    private  TextView titleActivity2;
    private  TextView questionText;
    private LinearLayout wrapper;

    // methods
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
        this.createQuestionView();
    }

    public void createQuestionView() {
        if ((MainActivity.questionChoicesIterator.hasNext())) {
            QuestionChoices questionChoices = MainActivity.questionChoicesIterator.next();
            questionText.setText(questionChoices.question.questionText);
            wrapper.removeAllViews();
            String title = "Question " + questionChoices.question.questionId + "/" + Defines.QUESTIONS.length;
            this.titleActivity2.setText(title);
            for (Choice choice : questionChoices.choices)
                this.createChoiceView(choice);
        }
        else this.createScoreView();
    }
    public void createChoiceView(Choice choice) {
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
    @SneakyThrows
    public void createScoreView() {
        Future<Integer> future = MainActivity.executor.submit(() -> {
            UserAnswers userAnswers = MainActivity.userDao.getUserAnswer(MainActivity.currentUser);
            int i = 0;
            for(Answer answer : userAnswers.answers) {
                if(answer.text.equals(Defines.ANSWERS[i++]))
                    userAnswers.user.score++;
            }
            MainActivity.userDao.update(userAnswers.user);
            return userAnswers.user.score;
        });
        Dialog dialog = new Dialog(MainActivity2.this, "Quiz finished !", "Your score is " + future.get() + "/" + Defines.QUESTIONS.length);
        dialog.show();
    }

    public void saveAnswer(View view) {
        MainActivity.executor.execute(() -> {
            String answerStr = ((Button)view).getText().toString();
            User user = MainActivity.userDao.getByUsername(MainActivity.currentUser);
            AnswerDao answerDao = MainActivity.database.answerDao();
            Answer answer  = new Answer(0, user.id, answerStr);
            answerDao.save(answer);
        });
        this.createQuestionView();
    }
}