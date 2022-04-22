package com.example.quizexo.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAnswers {
    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<Answer> answers;

}
