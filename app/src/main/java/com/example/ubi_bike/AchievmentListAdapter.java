package com.example.ubi_bike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievmentListAdapter extends RecyclerView.Adapter<AchievmentListAdapter.AchievmentViewHolder> {

    private List<Achievment> achievmentList;

    public AchievmentListAdapter(List<Achievment> achievmentList) {
        this.achievmentList = achievmentList;
    }

    @NonNull

    public AchievmentListAdapter.AchievmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievment_item, parent, false);
        return new AchievmentListAdapter.AchievmentViewHolder(view);
    }


    public void onBindViewHolder(@NonNull AchievmentListAdapter.AchievmentViewHolder holder, int position) {
        Achievment achievment = achievmentList.get(position);
        holder.achievmentNameTextView.setText(achievment.getName());
        holder.achievmentDescription.setText(achievment.getDescription());
    }


    public int getItemCount() {
        return achievmentList.size();
    }

    public static class AchievmentViewHolder extends RecyclerView.ViewHolder {
        public TextView achievmentNameTextView;
        public TextView achievmentDescription;

        public AchievmentViewHolder(@NonNull View itemView) {
            super(itemView);
            achievmentNameTextView = itemView.findViewById(R.id.achievment_name);
            achievmentDescription = itemView.findViewById(R.id.achievment_desc);


        }
    }
}
