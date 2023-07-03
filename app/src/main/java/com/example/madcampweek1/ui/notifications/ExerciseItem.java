package com.example.madcampweek1.ui.notifications;

import android.widget.EditText;

public class ExerciseItem {
    private EditText nameExercise;
    private EditText timeExercise;

    public ExerciseItem() {
    }

    public EditText getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(EditText nameExercise) {
        this.nameExercise = nameExercise;
    }

    public EditText getTimeExercise() {
        return timeExercise;
    }

    public void setTimeExercise(EditText timeExercise) {
        this.timeExercise = timeExercise;
    }
}
