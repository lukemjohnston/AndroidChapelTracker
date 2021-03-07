package com.example.chapeltrackerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> chapelTitles = new ArrayList<>();
    private ArrayList<String> chapelTimes = new ArrayList<>();
    private ArrayList<String> chapelLocations = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context inContext, ArrayList<String> inChapelTitles, ArrayList<String> inChapelTimes, ArrayList<String> inChapelLocations) {
        chapelTitles = inChapelTitles;
        chapelTimes = inChapelTimes;
        chapelLocations = inChapelLocations;
        mContext = inContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.chapel_location.setText(chapelLocations.get(position));
        holder.chapel_title.setText(chapelTitles.get(position));
        holder.chapel_time.setText(chapelTimes.get(position));

    }

    @Override
    public int getItemCount() {
        return chapelTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapel_title;
        TextView chapel_time;
        TextView chapel_location;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapel_title = itemView.findViewById(R.id.chapel_title);
            chapel_time = itemView.findViewById(R.id.chapel_time);
            chapel_location = itemView.findViewById(R.id.chapel_location);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
