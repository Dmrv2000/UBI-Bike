package com.example.ubi_bike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AchievementListAdapterAdmin extends RecyclerView.Adapter<AchievementListAdapterAdmin.AchievementViewHolder> {

    private final List<Achievement> listaAchievements;
    private Context context;

    public AchievementListAdapterAdmin(List<Achievement> listaAchievements, Context context) {
        this.listaAchievements = listaAchievements;
        this.context = context;
    }

    @NonNull

    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_item_admin, parent, false);
        context = parent.getContext();
        return new AchievementViewHolder(view);
    }


    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = listaAchievements.get(position);
        holder.achievementNameTextView.setText(achievement.getName());
        holder.achievementDescription.setText(achievement.getDescription());
        holder.achievementPoints.setText("Pontos: " + achievement.getPoints());

        holder.crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerAchievement(achievement);
            }
        });
    }

    private void removerAchievement(Achievement achievement) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar");
        builder.setMessage("Tem a certeza que quer eliminar este achievement?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = listaAchievements.indexOf(achievement);
                if (position != -1) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("store").document(achievement.getName())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listaAchievements.remove(achievement);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, listaAchievements.size());
                                    Toast.makeText(context, "Achievement removido com successo.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public int getItemCount() {
        return listaAchievements.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder {
        public TextView achievementNameTextView;
        public TextView achievementDescription;
        public TextView achievementPoints;
        public ImageView crossButton;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            achievementNameTextView = itemView.findViewById(R.id.achievment_name);
            achievementDescription = itemView.findViewById(R.id.achievment_desc);
            achievementPoints = itemView.findViewById(R.id.achievment_points);
            crossButton = itemView.findViewById(R.id.crossButton);
        }
    }
}
