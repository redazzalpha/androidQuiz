package com.example.quizexo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Choice {
    @PrimaryKey(autoGenerate = true)
    public long  choiceId;
    public long  parentId;
    public String choiceText;

}
