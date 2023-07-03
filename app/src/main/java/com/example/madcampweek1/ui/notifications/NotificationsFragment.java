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
        cardItemArrayList = new ArrayList<>();



        // '루틴 추가' 버튼을 클릭하면 다이얼로그를 띄웁니다.
        binding.btnAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                adapter = new MyAdapter(cardItemArrayList);
                recyclerViewRoutines.setAdapter(adapter);


                cardItemArrayList.add(new CardItem("hello", "world"));
                adapter.notifyDataSetChanged();

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