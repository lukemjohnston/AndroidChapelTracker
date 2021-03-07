package com.example.chapeltrackerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Completed_RecViewAdpt extends RecyclerView.Adapter<Completed_RecViewAdpt.Com_ViewHolder> {

    private ArrayList<String> dates;
    private ArrayList<String> names;
    private ArrayList<Integer> credits;
    Context context;

    public Completed_RecViewAdpt(Context con, ArrayList<String> d, ArrayList<String> n, ArrayList<Integer> c) {
        context = con;
        dates = d;
        names = n;
        credits = c;
    }


    @NonNull
    @Override
    public Com_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater c_inflater = LayoutInflater.from(context);
        View c_view = c_inflater.inflate(R.layout.content_activity_chapels_completed, parent, false);
        return new Com_ViewHolder(c_view);
    }


    @Override
    public void onBindViewHolder(@NonNull Com_ViewHolder holder, int position) {
        holder.textd.setText(dates.get(position));
        holder.textn.setText(names.get(position));
        holder.textc.setText(String.valueOf(credits.get(position)));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class Com_ViewHolder extends RecyclerView.ViewHolder {

        TextView textd, textn, textc;

        public Com_ViewHolder(@NonNull View itemView) {
            super(itemView);
            textd = itemView.findViewById(R.id.com_dateTextView);
            textn = itemView.findViewById(R.id.com_nameTextView);
            textc = itemView.findViewById(R.id.com_creditTextView);

        }
    }
}

