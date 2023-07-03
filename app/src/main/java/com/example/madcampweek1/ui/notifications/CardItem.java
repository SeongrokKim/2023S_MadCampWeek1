package com.example.madcampweek1.ui.notifications;

import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CardItem {
    private ArrayList<ExerciseItem> exerciseItemArrayList;
    private EditText title;
    private EditText cycles;

    public CardItem() {
        exerciseItemArrayList = new ArrayList<>();
    }

    public ArrayList<ExerciseItem> getExerciseItemArrayList() {
        return exerciseItemArrayList;
    }
    public EditText getEditTextTitle() {
        return title;
    }
    public EditText getEditTextCycles() {
        return cycles;
    }
}
