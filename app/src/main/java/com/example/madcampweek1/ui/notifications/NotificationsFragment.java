package com.example.madcampweek1.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentNotificationsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerViewRoutines;
    private RecyclerView recyclerViewExercises;
    private ArrayList<CardItem> cardItemArrayList;
    private Button btnAddRoutine;
    private Button buttonAdd;
    private MyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnAddRoutine = root.findViewById(R.id.btnAddRoutine);
        recyclerViewRoutines = root.findViewById(R.id.recyclerViewRoutines);
        recyclerViewExercises = root.findViewById(R.id.recyclerViewExercises);
        cardItemArrayList = new ArrayList<>();



        // '루틴 추가' 버튼을 클릭하면 다이얼로그를 띄웁니다.
        binding.btnAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_routine, null);



                builder.setView(dialogView)
                        .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 완료 버튼을 클릭했을 때 처리할 코드 추가
                                EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
                                EditText editTextExercise = dialogView.findViewById(R.id.editTextExercise);
                                EditText editTextTime = dialogView.findViewById(R.id.editTextTime);

                                String title = editTextTitle.getText().toString();
                                String exercise = editTextExercise.getText().toString();
                                int time = Integer.parseInt(editTextTime.getText().toString());

                                // 루틴 추가 작업을 수행할 코드 추가

                                dialog.dismiss(); // 다이얼로그 닫기
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // 다이얼로그 닫기
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                buttonAdd = dialogView.findViewById(R.id.buttonAdd);
                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        adapter = new MyAdapter(cardItemArrayList);
                        recyclerViewExercises.setAdapter(adapter);

                        EditText exercise = dialogView.findViewById(R.id.editTextExercise);
                        EditText time = dialogView.findViewById(R.id.editTextTime);

                        cardItemArrayList.add(new CardItem(exercise.getText().toString(), time.getText().toString()));
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}