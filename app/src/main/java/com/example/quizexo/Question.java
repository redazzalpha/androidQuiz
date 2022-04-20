package com.example.quizexo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Question {
    @PrimaryKey(autoGenerate = true)
    public long  questionId;
    public String questionText;
}
