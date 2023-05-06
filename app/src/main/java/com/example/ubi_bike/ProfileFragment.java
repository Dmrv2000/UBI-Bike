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

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
        // require a empty public constructor
    }

    private ArrayList<Achievment> achievmentsList;
    private RecyclerView achievmentRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve products from Firestore
        db.collection("achievments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                achievmentsList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    //Product product = documentSnapshot.toObject(Product.class);
                    Achievment achievment = new Achievment(documentSnapshot.getId(),documentSnapshot.getString("description"));
                    achievmentsList.add(achievment);
                    //Log.d("Taf",documentSnapshot.getString("amount"));
                }


                // Create and set adapter for RecyclerView
                achievmentRecyclerView = container.findViewById(R.id.achievmets);
                achievmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                AchievmentListAdapter achievmentListAdapter = new AchievmentListAdapter(achievmentsList);
                achievmentRecyclerView.setAdapter(achievmentListAdapter);
            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}