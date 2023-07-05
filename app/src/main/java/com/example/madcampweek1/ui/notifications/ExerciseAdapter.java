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
        holder.btnBunPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.changeBun(+1);
            }
        });
        holder.btnBunMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.changeBun(-1);
            }
        });
        holder.btnChoPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.changeCho(+5);
            }
        });
        holder.btnChoMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                holder.changeCho(-5);
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
        private Button btnChoPlus;
        private Button btnChoMinus;
        private Button btnBunPlus;
        private Button btnBunMinus;


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.nameExercise);
            timeExerciseBun = itemView.findViewById(R.id.timeExerciseBun);
            timeExerciseCho = itemView.findViewById(R.id.timeExerciseCho);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnChoPlus = itemView.findViewById(R.id.btnChoPlus);
            btnChoMinus = itemView.findViewById(R.id.btnChoMinus);
            btnBunPlus = itemView.findViewById(R.id.btnBunPlus);
            btnBunMinus = itemView.findViewById(R.id.btnBunMinus);
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
        public int getBun(){
            String tp = timeExerciseBun.getText().toString();
            if (tp.equalsIgnoreCase("")) return 0;
            else return Integer.parseInt(tp);
        }
        public void setBun(int bun){timeExerciseBun.setText(Integer.toString((bun + 60)% 60));}
        public void changeBun(int diff){setBun(diff + getBun());}
        public int getCho(){
            String tp = timeExerciseCho.getText().toString();
            if (tp.equalsIgnoreCase("")) return 0;
            else return Integer.parseInt(tp);
        }
        public void setCho(int bun){timeExerciseCho.setText(Integer.toString((bun + 60)% 60));}
        public void changeCho(int diff){setCho(diff + getCho());}
    }
}
