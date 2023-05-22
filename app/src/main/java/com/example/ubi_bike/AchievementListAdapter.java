package com.example.ubi_bike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementListAdapter extends RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder> {

    private final List<Achievement> achievementList;

    public AchievementListAdapter(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    @NonNull

    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_item, parent, false);
        return new AchievementViewHolder(view);
    }


    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = achievementList.get(position);
        holder.achievementNameTextView.setText(achievement.getName());
        holder.achievementDescription.setText(achievement.getDescription());
        holder.achievementPoints.setText("Pontos: " + achievement.getPoints());
    }


    public int getItemCount() {
        return achievementList.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView achievementNameTextView;
        public TextView achievementDescription;
        public TextView achievementPoints;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            achievementNameTextView = itemView.findViewById(R.id.achievment_name);
            achievementDescription = itemView.findViewById(R.id.achievment_desc);
            achievementPoints = itemView.findViewById(R.id.achievment_points);
        }
    }
}
