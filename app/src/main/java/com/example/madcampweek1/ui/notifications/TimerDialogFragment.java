package com.example.madcampweek1.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.madcampweek1.MainActivity;
import com.example.madcampweek1.databinding.FragmentDashboardBinding;
import com.example.madcampweek1.databinding.FragmentTimerBinding;
import com.example.madcampweek1.databinding.FragmentTimerDialogBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerDialogFragment extends DialogFragment {
    public static final String TAG_TIMER_DIALOG = "dialog_timer";
    private FragmentTimerDialogBinding binding;
    private TextView textViewName;
    private TextView textViewExercise;
    private TextView textViewReps;
    private TextView textViewTimer;
    private Button buttonSkip;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonExit;
    private int exerciseIndex = -1;

    private int reps = -1;
    private String routineName = "Err";
    private List<Map<String, String>> exercises = new ArrayList<>();
    private MyTimer myTimer;


    public static TimerDialogFragment getInstance(){
        TimerDialogFragment e = new TimerDialogFragment();
        return e;
    }

    public static TimerDialogFragment getInstance(String routineName, int reps, List<Map<String, String>> exercises){
        TimerDialogFragment e = new TimerDialogFragment();
        e.exercises = exercises;
        e.reps = reps;
        e.routineName = routineName;
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        binding = FragmentTimerDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textViewName = binding.textViewName;
        textViewExercise = binding.textViewExercise;
        textViewTimer = binding.textViewTimer;

        textViewReps = binding.textViewReps;
        buttonExit = binding.buttonExit;
        buttonSkip = binding.buttonSkip;
        buttonStart = binding.buttonStart;
        buttonStop = binding.buttonStop;

        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTimer.cancel();
                // PopupWindow를 닫기
                dismiss();
            }
        });
        //Toast.makeText(getContext(), "Onclick1", Toast.LENGTH_SHORT).show();

        binding.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skip 버튼 클릭 이벤트 처리
                Toast.makeText(getContext(), "OnclickSkip", Toast.LENGTH_SHORT).show();

                if(myTimer != null)
                    skipTimer();
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start 버튼 클릭 이벤트 처리
                // ...
                Toast.makeText(getContext(), "OnclickStart", Toast.LENGTH_SHORT).show();
                if(myTimer!=null)
                    myTimer.cancel();
                myTimer = new MyTimer();
                myTimer.start();

            }
        });

        binding.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop 버튼 클릭 이벤트 처리
                // ...
                Toast.makeText(getContext(), "OnclickStop", Toast.LENGTH_SHORT).show();
                if(myTimer != null)
                    stopTimer();
            }
        });

        //임시 값 대입
        //initTemporary();

        // 데이터 활용
        textViewName.setText(routineName);
        textViewExercise.setText(exercises.get(0).get("name"));
        textViewTimer.setText(exercises.get(0).get("time"));
        textViewReps.setText(Integer.toString(reps));
        exerciseIndex = 0;
        setCancelable(false);
        return root;
    }
    private void stopTimer(){
        myTimer.cancel();
    }

    private void skipTimer(){
        setTime(0);
        myTimer.cancel();
        myTimer = new MyTimer(0,1000);
        myTimer.start();
    }


    class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public MyTimer(int time) {
            super(time * 1000, 1000);
        }
        public MyTimer() {
            this(timerToInt());
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int restTime = timerToInt();
            restTime -= 1;
            //Toast.makeText(getContext(), Integer.toString(restTime), Toast.LENGTH_SHORT).show();

            textViewTimer.setText(timerFromInt(restTime));
            if (restTime == -1) {
                onFinish();
            }
        }

        @Override
        public void onFinish() {
            cancel();
            textViewTimer.setText("00:00:00");
            //소리내기
            //Toast.makeText(getContext(), "Timer End!", Toast.LENGTH_SHORT).show();
            //다음거 실행
            if (exerciseIndex == exercises.size() - 1) {
                //루틴 내 최종에 다다름

                if (reps == 1) {
                    //축하 알림
                    Toast.makeText(getContext(), "REALLY END!", Toast.LENGTH_SHORT).show();
                } else {
                    //reps 남음
                    exerciseIndex = 0;
                    reps -= 1;
                    if(reps<0) reps = 0; //0일때 무한반복용
                    textViewReps.setText(Integer.toString(reps));
                    //Toast.makeText(getContext(),Integer.toString(reps), Toast.LENGTH_SHORT ).show();

                    startOnePart();
                }

            } else {
                //남은 운동 있음
                exerciseIndex += 1;
                startOnePart();
            }

        }
    }
    public void startOnePart(){
        //Toast.makeText(getContext(),Integer.toString(exerciseIndex), Toast.LENGTH_SHORT ).show();
        textViewExercise.setText(exercises.get(exerciseIndex).get("name"));
        textViewTimer.setText(exercises.get(exerciseIndex).get("time"));
        myTimer.cancel();
        myTimer = new MyTimer(timerToInt());
        myTimer.start();


    }
    public int timerToInt(String timerViewText){
        String[] times = timerViewText.split(":");
        return Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]) ;
    }
    public int timerToInt(){
        return timerToInt((String)textViewTimer.getText());
    }
    @SuppressLint("DefaultLocale")
    public String timerFromInt(int time){
        return String.format("%02d:%02d:%02d",time/3600, (time%3600)/60, time%60);
    }
    public void setTime(int time){
        textViewTimer.setText(timerFromInt(time));
    }
    public int getReps(){
        return Integer.parseInt((String) textViewReps.getText());
    }

    public void initTemporary(){
        //임시 값
        routineName = "Tmp Rt";
        reps = 0;
        exercises = new ArrayList<>();
        Map<String, String> exMap = new HashMap<>();
        exMap.put("name", "Exc1");
        exMap.put("time", "00:01:03");
        exercises.add(exMap);
        Map<String, String> exMap2 = new HashMap<>();
        exMap2.put("name", "Exc2");
        exMap2.put("time", "00:00:02");
        exercises.add(exMap2);
        Map<String, String> exMap3 = new HashMap<>();
        exMap3.put("name", "Exc3");
        exMap3.put("time", "00:00:03");
        exercises.add(exMap3);
    }
}
