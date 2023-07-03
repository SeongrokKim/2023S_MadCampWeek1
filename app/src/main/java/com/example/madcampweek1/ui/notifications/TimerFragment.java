package com.example.madcampweek1.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.madcampweek1.MainActivity;
import com.example.madcampweek1.databinding.FragmentTimerBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerFragment extends Fragment {

    private static final String TAG = "Timer Fragment";
    private FragmentTimerBinding binding;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "OnCreate", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), getArguments().toString(), Toast.LENGTH_SHORT).show();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getContext(), "OncreateView", Toast.LENGTH_SHORT).show();

        binding = FragmentTimerBinding.inflate(inflater, container, false);
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
                // PopupWindow를 닫기
                if (getActivity() != null) {
                    PopupWindow popupWindow = ((MainActivity) getActivity()).getPopupWindow();
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }

                // 원래 Fragment로 돌아가기
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        Toast.makeText(getContext(), "Onclick1", Toast.LENGTH_SHORT).show();

        binding.buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skip 버튼 클릭 이벤트 처리
                // ...
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start 버튼 클릭 이벤트 처리
                // ...
            }
        });

        binding.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop 버튼 클릭 이벤트 처리
                // ...
            }
        });
        Toast.makeText(getContext(), "Onclick2", Toast.LENGTH_SHORT).show();


        // 데이터 추출
        Bundle arguments = getArguments();
        if (true || arguments != null) {
//            // argument로 인자 넣어주기
//            routineName = arguments.getString("routineName");
//            reps = arguments.getInt("reps");
//            exercises = (List<Map<String, String>>) arguments.getSerializable("exercises");

            //임시 값
            routineName = "Tmp Rt";
            reps = 3;
            exercises = new ArrayList<>();
            Map<String, String> exMap = new HashMap<>();
            exMap.put("name", "TmpEx");
            exMap.put("time", "00:12:34");
            exercises.add(exMap);
            exMap.put("name", "TmpRest");
            exMap.put("time", "00:00:10");
            exercises.add(exMap);

            // 데이터 활용
            textViewName.setText(routineName);
            textViewExercise.setText(exercises.get(0).get("name"));
            textViewTimer.setText(exercises.get(0).get("time"));
            textViewReps.setText(Integer.toString(reps));
            exerciseIndex = 0;
        }
        else{
            Toast.makeText(getContext(), "헤헤 죠졌네 이거", Toast.LENGTH_SHORT).show();
        }

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
