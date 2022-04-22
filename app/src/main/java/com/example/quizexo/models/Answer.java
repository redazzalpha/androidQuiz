package com.example.quizexo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long userId;
    public String text;

}
