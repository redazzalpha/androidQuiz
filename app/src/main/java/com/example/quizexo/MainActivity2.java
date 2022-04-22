package com.example.quizexo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizexo.defines.Defines;
import com.example.quizexo.models.Choice;
import com.example.quizexo.models.QuestionChoices;
import com.google.android.material.button.MaterialButton;

import java.util.Iterator;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView questionText = findViewById(R.id.questionText);
        QuestionChoices questionChoices;

        if ((MainActivity.questionChoicesIterator.hasNext())) {
            questionChoices = (QuestionChoices) MainActivity.questionChoicesIterator.next();
            questionText.setText(questionChoices.question.questionText);
            for (Choice choice : questionChoices.choices)
                this.createChoice(choice);
        }
    }

    public void createChoice(Choice choice) {
        LinearLayout wrapper = findViewById(R.id.activity2Wrapper);
        Button btnField = new Button(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(Defines.BTN_MARGINS_LF, Defines.BTN_MARGINS_TB, Defines.BTN_MARGINS_LF, Defines.BTN_MARGINS_TB);

//        btnField.setId(Integer.parseInt("123"));
        btnField.setLayoutParams(params);
        btnField.setText(choice.choiceText);
        btnField.setBackgroundColor(Color.parseColor("#95c500"));

        wrapper.addView(btnField);
    }
}