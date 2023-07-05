package com.example.madcampweek1.ui.notifications;

import static android.content.Context.VIBRATOR_SERVICE;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.madcampweek1.MainActivity;
import com.example.madcampweek1.R;
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
    private ProgressBar progressBar;
    private Vibrator vibrator ;
    private MediaPlayer mediaPlayer;


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
    @Override
    public void onStart() {
        super.onStart();
        // 다이얼로그의 크기를 디바이스 크기의 95%로 설정
        if (getDialog() != null) {
            // 디바이스의 가로 크기 가져오기
            int deviceWidth = getResources().getDisplayMetrics().widthPixels;
            // 디바이스의 세로 크기 가져오기
            int deviceHeight = getResources().getDisplayMetrics().heightPixels;

            // 다이얼로그의 가로 크기 설정 (디바이스 가로 크기의 95%)
            int dialogWidth = (int) (deviceWidth * 0.95);
            // 다이얼로그의 세로 크기 설정 (디바이스 세로 크기의 95%)
            int dialogHeight = (int) (deviceHeight * 0.95);

            // 다이얼로그의 크기를 설정
            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        }
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
        progressBar = binding.progressbar;

        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myTimer!=null)myTimer.cancel();
                // PopupWindow를 닫기
                dismiss();
            }
        });
        //Toast.makeText(getContext(), "Onclick1", Toast.LENGTH_SHORT).show();

        binding.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skip 버튼 클릭 이벤트 처리
                //Toast.makeText(getContext(), "OnclickSkip", Toast.LENGTH_SHORT).show();

                if(myTimer != null) skipTimer();
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start 버튼 클릭 이벤트 처리
                // ...
                //Toast.makeText(getContext(), "OnclickStart", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(getContext(), "OnclickStop", Toast.LENGTH_SHORT).show();
                if(myTimer != null)
                    stopTimer();
            }
        });

        //임시 값 대입
        //initTemporary();
        // 진동 객체 얻기
        vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
        // 데이터 활용
        exerciseIndex = 0;
        textViewName.setText(routineName);
        textViewExercise.setText(exercises.get(0).get("name"));
        setTime(exercises.get(0).get("time"));
        setReps(reps);
        setCancelable(false);
        return root;
    }
    private void stopTimer(){
        myTimer.cancel();
    }

    private void skipTimer(){
        setTime(0);
        if(myTimer!=null)
            myTimer.cancel();
        myTimer = new MyTimer(0,1000);
        myTimer.start();
    }


    class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public MyTimer(int time) {
            this(time * 1000, 1000);
        }
        public MyTimer() {
            this(timerToInt());
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int restTime = timerToInt();
            restTime -= 1;
            //Toast.makeText(getContext(), Integer.toString(restTime), Toast.LENGTH_SHORT).show();

            setTime(restTime);
            if (restTime == 0) {
                onFinish();
            }

        }

        @Override
        public void onFinish() {
            cancel();
            setTime(0);
            //진동 및 소리내기
            beep();
            //버전이 26이상이면
            beepVib(5);
            //Toast.makeText(getContext(), "Timer End!", Toast.LENGTH_SHORT).show();

            //다음거 실행
            if (exerciseIndex == exercises.size() - 1) {
                //루틴 내 최종에 다다름

                if (reps == 1) {
                    //축하 알림
                    //Toast.makeText(getContext(), "REALLY END!", Toast.LENGTH_SHORT).show();
                } else {
                    //reps 남음
                    exerciseIndex = 0;
                    reps -= 1;
                    if(reps<0) reps = 0; //0일때 무한반복용
                    setReps(reps);
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
        setTime(exercises.get(exerciseIndex).get("time"));
        if(myTimer!=null)myTimer.cancel();
        myTimer = new MyTimer(timerToInt());
        myTimer.start();


    }
    public int timerToInt(String timerViewText){
        String[] times = timerViewText.split(":");
        int res;
        switch (times.length){
            case 3:
                res = Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]) ;
                break;
            case 2:
                res = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]) ;
                break;
            default:
                res = Integer.parseInt(times[0]) ;
                break;
        }
        return res;
    }
    public int timerToInt(){
        return timerToInt((String)textViewTimer.getText());
    }
    @SuppressLint("DefaultLocale")
    public String timerFromInt(int time){
        String res;
        if (time/3600 > 0)
            res = String.format("%d:%02d:%02d",time/3600, (time%3600)/60, time%60);
        else if(time / 60 > 0)
            res = String.format("%d:%02d", (time%3600)/60, time%60);
        else
            res = String.format("%d", time%60);
        return res;
    }
    public void setTime(String s){
        progressBar.setProgress(100 * (timerToInt(s)) / timerToInt(exercises.get(exerciseIndex).get("time")) );
        setBarColor(exerciseIndex);
        textViewTimer.setText(timerFromInt(timerToInt(s)));
    }
    public void setTime(int time){
        setTime(timerFromInt(time));
    }
    public int getReps(){
        return Integer.parseInt(((String) textViewReps.getText()).split(" ")[0]);
    }
    public void setReps(int reps){
        textViewReps.setText(Integer.toString(reps)+" reps");
    }
    public void setBarColor(int index){
        String[][] colorCombos = {
                {"#FF0000", "#806B6B"},  // Red bar with dark reddish background
                {"#00FF00", "#6B806B"},  // Green bar with dark greenish background
                {"#FF00FF", "#806B80"},  // Magenta bar with dark purplish background
                {"#FFFF00", "#80806B"},  // Yellow bar with dark yellowish background
                {"#0000FF", "#6B6B80"},  // Blue bar with dark bluish background
                {"#00FFFF", "#6B8080"},  // Cyan bar with dark cyanish background
                {"#FFA500", "#8A5700"},  // Orange bar with dark orangish background
                {"#008000", "#1F522E"},  // Dark green bar with dark greenish background
                {"#800080", "#5E1A66"},  // Purple bar with dark purplish background
                {"#FFFFFF", "#1A1A1A"}   // Black bar with dark grayish background
        };
        //Toast.makeText(getContext(), colorCombos[index%colorCombos.length][0]+colorCombos[index%colorCombos.length][1], Toast.LENGTH_SHORT).show();
        setBarColor(colorCombos[index%colorCombos.length][0], colorCombos[index%colorCombos.length][1]);

    }
    public void setBarColor(String barColor, String backColor) {
// ProgressBar의 progressBackgroundTint 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.parseColor(backColor)));
        }

// ProgressBar의 progressTint 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor(barColor)));
        }
    }

    private void beep() {
        //Toast.makeText(getContext(), "Beep", Toast.LENGTH_SHORT).show();
        // 이전에 재생 중인 음악이 있다면 중지하고 리소스를 해제합니다
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // 새로운 타이머 시작을 알리는 알림음을 재생합니다
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.beepsound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 재생이 완료된 후에 수행할 동작
                mediaPlayer.release(); // MediaPlayer 해제
                mediaPlayer = null; // 객체 초기화
            }
        });
        mediaPlayer.start(); // 재생 시작
    }


    public void beepVib(int last){
        //Toast.makeText(getContext(), "BeepVib", Toast.LENGTH_SHORT).show();
        if(Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100 * last, 250));
        } else {    //26보다 낮으면
            vibrator.vibrate(100 * last);
        }
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
