package com.example.quizexo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Choice {
    @PrimaryKey
    public long  choiceId;
    public long  parentId;
    public String choiceText;

}
