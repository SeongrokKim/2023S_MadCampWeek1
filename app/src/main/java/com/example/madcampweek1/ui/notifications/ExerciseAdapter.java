package com.example.madcampweek1.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.google.android.material.badge.BadgeUtils;

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

        holder.setExerciseItem(exerciseItem);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseItemArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, exerciseItemArrayList.size());
            }
        });
    }

    @Override
    public int getItemCount(){
        return exerciseItemArrayList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private EditText nameExercise;
        private EditText timeExerciseCho;
        private EditText timeExerciseBun;
        private ExerciseItem exerciseItem;
        private Button btnDelete;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.nameExercise);
            timeExerciseCho = itemView.findViewById(R.id.timeExerciseBun);
            timeExerciseBun = itemView.findViewById(R.id.timeExerciseCho);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        public ExerciseItem getExerciseItem(){
            return exerciseItem;
        }

        public void setExerciseItem(ExerciseItem exerciseItem) {
            this.exerciseItem = exerciseItem;
        }

        public EditText getNameExercise() {
            return nameExercise;
        }

        public void setNameExercise(EditText nameExercise) {
            this.nameExercise = nameExercise;
        }

        public EditText getTimeExerciseBun() {
            return timeExerciseBun;
        }

        public void setTimeExerciseBun(EditText timeExercise) {
            this.timeExerciseBun = timeExercise;
        }
        public EditText getTimeExerciseCho() {
            return timeExerciseCho;
        }

        public void setTimeExerciseCho(EditText timeExercise) {
            this.timeExerciseCho = timeExercise;
        }
    }
}
