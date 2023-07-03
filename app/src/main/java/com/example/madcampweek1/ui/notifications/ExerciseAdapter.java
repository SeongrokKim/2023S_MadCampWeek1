package com.example.madcampweek1.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<ExerciseItem> exerciseItemArrayList;
    ExerciseAdapter(ArrayList<ExerciseItem> exerciseItemArrayList){
        this.exerciseItemArrayList = exerciseItemArrayList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_item, viewGroup, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseItem exerciseItem = exerciseItemArrayList.get(position);
    }

    @Override
    public int getItemCount(){
        return exerciseItemArrayList.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        EditText editTextExercise;
        EditText editTextTime;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextExercise = itemView.findViewById(R.id.editTextExercise);
            editTextTime = itemView.findViewById(R.id.editTextTime);
        }
    }
}
