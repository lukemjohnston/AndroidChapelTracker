package com.example.chapeltrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailedCal_RecViewAdpt extends RecyclerView.Adapter<DetailedCal_RecViewAdpt.Det_ViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<String> times;
    private ArrayList<String> locations;
    private ArrayList<String> dates;
    Context context;

    public DetailedCal_RecViewAdpt(Context con, ArrayList<String> tit, ArrayList<String> tim, ArrayList<String> loc, ArrayList<String> dat) {
        context = con;
        titles = tit;
        times = tim;
        dates = dat;
        locations = loc;
    }


    @NonNull
    @Override
    public Det_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater c_inflater = LayoutInflater.from(context);
        View c_view = c_inflater.inflate(R.layout.content_detailed_calendar, parent, false);
        return new Det_ViewHolder(c_view);
    }

    @Override
    public void onBindViewHolder(@NonNull Det_ViewHolder holder, int position) {
        holder.texttit.setText(titles.get(position));
        holder.texttim.setText(times.get(position));
        holder.textloc.setText(locations.get(position));
        holder.textdat.setText(dates.get(position));
    }

    public int getItemCount() {
        return titles.size();
    }

    public class Det_ViewHolder extends RecyclerView.ViewHolder {

        TextView texttit, texttim, textloc, textdat;

        public Det_ViewHolder(@NonNull View itemView) {
            super(itemView);
            texttit = itemView.findViewById(R.id.com_titleTextView);
            texttim = itemView.findViewById(R.id.com_timeTextView);
            textloc = itemView.findViewById(R.id.com_locationTextView);
            textdat = itemView.findViewById(R.id.com_date2TextView);

        }
    }
}
