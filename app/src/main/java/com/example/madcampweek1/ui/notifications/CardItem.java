package com.example.madcampweek1.ui.notifications;

public class CardItem {
    String exercise;
    String time;

    public CardItem(String exercise, String time){
        this.exercise = exercise;
        this.time = time;
    }

    public  String getExercise(){
        return exercise;
    }

    public void setExercise(String exercise){
        this.exercise = exercise;
    }

    public  String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }
}
