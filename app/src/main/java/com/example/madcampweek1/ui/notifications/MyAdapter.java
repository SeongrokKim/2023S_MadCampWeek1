package com.example.madcampweek1.ui.notifications;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
                exerciseAdapter.notifyItemInserted(cardItem.getExerciseItemArrayList().size()-1);

            }
        });

        cardItem.setTitle(holder.editTextTitle);
        cardItem.setCycles(holder.editTextCycles);

        holder.btnCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String title;
                int cycles;
                if (cardItem.getEditTextTitle().getText().toString() == null){
                    title = "루틴";
                }
                else {
                    title = cardItem.getEditTextTitle().getText().toString();
                }
                if (cardItem.getEditTextCycles().getText().toString() == null){
                    cycles = 1;
                }
                else{
                    cycles = Integer.parseInt(cardItem.getEditTextCycles().getText().toString());
                }
                ArrayList<Map<String, String>> exercises = new ArrayList<>();
                for (int i = 0; i < cardItem.getExerciseItemArrayList().size(); i++) {
                    ExerciseAdapter.ExerciseViewHolder exerciseViewHolder = (ExerciseAdapter.ExerciseViewHolder) holder.recyclerViewExercises.findViewHolderForAdapterPosition(i);
                    ExerciseItem exerciseItem = cardItem.getExerciseItemArrayList().get(i);
                    exerciseItem.setNameExercise(exerciseViewHolder.getNameExercise());
                    exerciseItem.setTimeExerciseBun(exerciseViewHolder.getTimeExerciseBun());
                    exerciseItem.setTimeExerciseCho(exerciseViewHolder.getTimeExerciseCho());
                    String exercise = "";
                    if (exerciseItem.getNameExercise().getText().toString() == null){
                        exercise = "운동" + String.valueOf(i+1);
                    }
                    else{
                        exercise = exerciseItem.getNameExercise().getText().toString();
                    }
                    String timeBun = "00";
                    if (exerciseItem.getTimeExerciseBun().getText().toString() == null){
                        timeBun = "00";
                    } else if (exerciseItem.getTimeExerciseBun().getText().toString().length() == 1) {
                        timeBun = "0"+exerciseItem.getTimeExerciseBun().getText().toString();
                    } else if (exerciseItem.getTimeExerciseBun().getText().toString().length() == 2) {
                        timeBun = exerciseItem.getTimeExerciseBun().getText().toString();
                    }
                    String timeCho = "00";
                    if (exerciseItem.getTimeExerciseCho().getText() == null){
                        timeCho = "00";
                    } else if (exerciseItem.getTimeExerciseCho().getText().toString().length() == 1) {
                        timeCho = "0"+exerciseItem.getTimeExerciseCho().getText().toString();
                    } else if (exerciseItem.getTimeExerciseCho().getText().toString().length() == 2) {
                        timeCho = exerciseItem.getTimeExerciseCho().getText().toString();
                    }
                    String time = "00:"+timeBun+":"+timeCho;
                    Toast.makeText(v.getContext(), time, Toast.LENGTH_SHORT);
                    Map<String, String> exMap = new HashMap<>();
                    exMap.put("name", exercise);
                    exMap.put("time", time);
                    exercises.add(exMap);
                }
                TimerDialogFragment timerDialogFragment = TimerDialogFragment.getInstance(title, cycles, exercises);
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                timerDialogFragment.show(fragmentManager, TimerDialogFragment.TAG_TIMER_DIALOG);


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardItemArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cardItemArrayList.size());
            }
        });
        holder.btnRepsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.changeRep(+1);
            }
        });
        holder.btnRepsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.changeRep(-1);
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
        private Button btnDelete;
        private EditText editTextTitle;
        private EditText editTextCycles;
        private Button btnRepsPlus;
        private Button btnRepsMinus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewExercises = itemView.findViewById(R.id.recyclerViewExercises);
            btnAddExercise = itemView.findViewById(R.id.btnAddExercise);
            btnCheck = itemView.findViewById(R.id.btnCheck);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            editTextCycles = itemView.findViewById(R.id.editTextNumOfCycles);
            editTextTitle = itemView.findViewById(R.id.editTextTitle);
            btnRepsPlus = itemView.findViewById(R.id.btnRepsPlus);
            btnRepsMinus = itemView.findViewById(R.id.btnRepsMinus);
        }
        public int getRep() {
            String tp = editTextCycles.getText().toString();
            if (tp.equalsIgnoreCase("")) {
                return -1;
            } else {
                return Integer.parseInt(tp);
            }
        }

        public void setRep(int bun) {
            editTextCycles.setText(Integer.toString(Math.max(0, bun)));
        }

        public void changeRep(int diff) {
            setRep(diff + getRep());
        }
    }
}
