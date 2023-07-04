package com.example.madcampweek1.ui.notifications;

import android.widget.EditText;

public class ExerciseItem {
    private EditText nameExercise;
    private EditText timeExerciseCho;
    private EditText timeExerciseBun;

    public ExerciseItem() {
    }

    public EditText getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(EditText nameExercise) {
        this.nameExercise = nameExercise;
    }

    public EditText getTimeExerciseBun() {
        return timeExerciseBun;
    }

    public void setTimeExerciseBun(EditText timeExercise) {
        this.timeExerciseBun = timeExercise;
    }
    public EditText getTimeExerciseCho() {
        return timeExerciseCho;
    }

    public void setTimeExerciseCho(EditText timeExercise) {
        this.timeExerciseCho = timeExercise;
    }
}
