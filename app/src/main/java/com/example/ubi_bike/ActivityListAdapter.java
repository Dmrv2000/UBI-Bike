package com.example.ubi_bike;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityViewHolder>{

    private List<Activity_class> activityList;

    public ActivityListAdapter(List<Activity_class> activityList) {
        this.activityList = activityList;
    }

    @NonNull

    public ActivityListAdapter.ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ActivityListAdapter.ActivityViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ActivityListAdapter.ActivityViewHolder holder, int position) {
        Activity_class activity = activityList.get(position);
        //holder.activityNameTextView.setText(achievment.getName());
        //holder.achievmentDescription.setText(achievment.getDescription());

        holder.activityDate.setText(activity.getDate());
        holder.activityTime.setText("Time: "+activity.getTime());
        holder.activityDistance.setText("Distance: "+activity.getDistance());

    }


    public int getItemCount() {
        return activityList.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        public TextView activityDate;
        public TextView activityTime;
        public TextView activityDistance;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            activityDate = itemView.findViewById(R.id.activity_date);
            activityTime = itemView.findViewById(R.id.activity_time);
            activityDistance = itemView.findViewById(R.id.activity_distance);


        }
    }
}
