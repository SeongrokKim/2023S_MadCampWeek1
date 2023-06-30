package com.example.madcampweek1.ui.dashboard;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {


    private final MutableLiveData<String> numLines;

    public DashboardViewModel() {

        numLines = new MutableLiveData<>();
        numLines.setValue("Number of Lines : 1");

    }

    public LiveData<String> getNumLines(){return numLines;}

    public int getNumLinesInt(){
        String[] splitArray = numLines.getValue().split(" ") ;
        return Integer.parseInt(splitArray[splitArray.length-1]);
    }
    public void increaseNumLines(){
        setLineNum(getNumLinesInt()+1);
    }
    public void setLineNum(int lineNum){
        numLines.setValue("Number of Lines : " + Integer.toString(lineNum));
    }
}