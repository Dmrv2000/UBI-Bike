package com.example.ubi_bike;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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

    private ArrayList<Achievement> achievmentsList;
    private ArrayList<Activity_class> activityList;
    private RecyclerView achievmentRecyclerView;
    private RecyclerView activityRecyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView username,email,points,bid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String uid = sharedPreferences.getString("userID", "");
        String uname = sharedPreferences.getString("username", "");
        String umail = sharedPreferences.getString("email", "");
        String upoints = sharedPreferences.getString("points", "");
        String ubid = sharedPreferences.getString("bikeid", "");


        username = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_mail);
        points = view.findViewById(R.id.points);
        bid = view.findViewById(R.id.profile_bikeid);


        username.setText(uname);
        email.setText(umail);
        points.setText("Points: " + upoints);
        bid.setText("Bike id: "+ubid);


        Log.d("Taf",uid);
        // Retrieve products from Firestore
        db.collection("users").document(uid).collection("u_achievments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                achievmentsList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    //Product product = documentSnapshot.toObject(Product.class);
                    Achievement achievement = new Achievement(documentSnapshot.getId(),documentSnapshot.getString("description"),documentSnapshot.getString("points"));
                    achievmentsList.add(achievement);

                }

                // Create and set adapter for RecyclerView
                achievmentRecyclerView = container.findViewById(R.id.achievmets);
                achievmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                AchievementListAdapter achievementListAdapter = new AchievementListAdapter(achievmentsList);
                achievmentRecyclerView.setAdapter(achievementListAdapter);
            }
        });

        db.collection("users").document(uid).collection("u_activity").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                activityList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Activity_class activity = new Activity_class(documentSnapshot.getString("date"),documentSnapshot.getString("distance"),documentSnapshot.getString("time"));
                    activityList.add(activity);

                }

                // Create and set adapter for RecyclerView
                activityRecyclerView = container.findViewById(R.id.activity);
                activityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ActivityListAdapter activityListAdapter = new ActivityListAdapter(activityList);
                activityRecyclerView.setAdapter(activityListAdapter);
            }
        });

        return view;
    }
}