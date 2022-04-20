package com.example.quizexo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class QuestionChoices {
    @Embedded
    public Question question;

    @Relation(parentColumn = "questionId", entityColumn = "parentId")
    public List<Choice> choices;
}
