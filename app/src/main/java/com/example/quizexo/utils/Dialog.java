package com.example.quizexo.utils;

import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.quizexo.MainActivity;
import com.example.quizexo.MainActivity2;
import com.example.quizexo.R;
import com.example.quizexo.defines.Defines;

import java.sql.ResultSet;

public class Dialog {
    private final AlertDialog dialog;

    public Dialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            context.startActivity(new Intent(context, MainActivity.class));
        });
        dialog = builder.create();

    }
    public void show() {
        this.dialog.show();
    }
}
