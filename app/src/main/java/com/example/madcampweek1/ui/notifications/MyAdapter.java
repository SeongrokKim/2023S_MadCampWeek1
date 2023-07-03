package com.example.madcampweek1.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.ui.home.HomeFragment;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<CardItem> cardItemArrayList;

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
        holder.exerciseTxt.setText(cardItemArrayList.get(position).getExercise());
        holder.timeTxt.setText(cardItemArrayList.get(position).getTime());
    }

    @Override
    public int getItemCount(){
        return cardItemArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseTxt;
        TextView timeTxt;

        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            this.exerciseTxt = itemView.findViewById(R.id.exercise);
            this.timeTxt = itemView.findViewById(R.id.time);
        }
    }
}
