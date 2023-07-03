package com.example.madcampweek1.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<CardItem> cardItemArrayList;


    public MyAdapter(ArrayList<CardItem> cardItemArrayList){
        this.cardItemArrayList= cardItemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        CardItem cardItem = cardItemArrayList.get(position);
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(cardItem.getExerciseItemArrayList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerViewExercises.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(cardItem.getExerciseItemArrayList().size());
        holder.recyclerViewExercises.setLayoutManager(layoutManager);
        holder.recyclerViewExercises.setAdapter(exerciseAdapter);
        holder.btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseItem exerciseItem = new ExerciseItem();
                cardItem.getExerciseItemArrayList().add(exerciseItem);
                exerciseAdapter.notifyDataSetChanged();
            }
        });

        holder.btnCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String title = cardItem.getEditTextTitle().getText().toString();
                int cycles = Integer.parseInt(cardItem.getEditTextCycles().getText().toString());
                ArrayList<Map<String, String>> exercises = new ArrayList<>();
                for (int i = 0; i < cardItem.getExerciseItemArrayList().size(); i++) {
                    ExerciseItem exerciseItem = cardItem.getExerciseItemArrayList().get(i);
                    String exercise = exerciseItem.getNameExercise().getText().toString();
                    String time = exerciseItem.getTimeExercise().getText().toString();
                    Map<String, String> exMap = new HashMap<>();
                    exMap.put("name", exercise);
                    exMap.put("time", time);
                    exercises.add(exMap);
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return cardItemArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerViewExercises;
        private Button btnAddExercise;
        private Button btnCheck;
        private Button btnStart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewExercises = itemView.findViewById(R.id.recyclerViewExercises);
            btnAddExercise = itemView.findViewById(R.id.btnAddExercise);
            btnCheck = itemView.findViewById(R.id.btnCheck);
            btnStart = itemView.findViewById(R.id.btnStart);
        }
    }
}
