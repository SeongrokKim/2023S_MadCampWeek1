package com.example.madcampweek1.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.madcampweek1.R;

import java.util.ArrayList;

public class CardItem {
    private ArrayList<ExerciseItem> exerciseItemArrayList;
    private EditText title;
    private EditText cycles;

    public CardItem() {
        exerciseItemArrayList = new ArrayList<ExerciseItem>();
    }

    public void setTitle(EditText title) {
        this.title = title;
    }

    public void setCycles(EditText cycles) {
        this.cycles = cycles;
    }

    public void setExerciseItemArrayList(ArrayList<ExerciseItem> exerciseItemArrayList) {
        this.exerciseItemArrayList = exerciseItemArrayList;
    }

    public ArrayList<ExerciseItem> getExerciseItemArrayList() {
        return exerciseItemArrayList;
    }
    public String getEditTextTitle() {
        return title.getText().toString();
    }
    public String getEditTextCycles() {
        return cycles.getText().toString();
    }
}
