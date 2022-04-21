package com.example.quizexo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Question {
    @PrimaryKey
    public long  questionId;
    public String questionText;
}
