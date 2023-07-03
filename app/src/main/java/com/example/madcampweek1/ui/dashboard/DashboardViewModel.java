package com.example.madcampweek1.ui.dashboard;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardViewModel extends ViewModel {


    private final MutableLiveData<String> numLines;

    public DashboardViewModel() {

        numLines = new MutableLiveData<>();
        numLines.setValue("Number of Lines : 0");
        dataListLiveData.setValue(new ArrayList<>());
    }

    public LiveData<String> getNumLines(){return numLines;}
    private final MutableLiveData<List<Map<String, String>>> dataListLiveData = new MutableLiveData<>();

    public void setDataList(List<Map<String, String>> dataList) {
        dataListLiveData.setValue(dataList);
    }

    public List<Map<String, String>> getDataList() {
        return dataListLiveData.getValue();
    }
    public LiveData<List<Map<String, String>>> getDataListLive() {
        return dataListLiveData;
    }
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