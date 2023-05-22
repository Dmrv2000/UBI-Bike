package com.example.ubi_bike;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
        // require a empty public constructor
    }

    private ArrayList<Achievement> achievementsList;
    private RecyclerView achievementRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve products from Firestore
        db.collection("achievments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                achievementsList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Achievement achievement = new Achievement(documentSnapshot.getId(), documentSnapshot.getString("description"), documentSnapshot.getString("points"));
                    achievementsList.add(achievement);
                }

                // Create and set adapter for RecyclerView
                achievementRecyclerView = container.findViewById(R.id.achievmets);
                achievementRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                AchievementListAdapter achievementListAdapter = new AchievementListAdapter(achievementsList);
                achievementRecyclerView.setAdapter(achievementListAdapter);
            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}