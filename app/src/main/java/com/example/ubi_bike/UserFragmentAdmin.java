package com.example.ubi_bike;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class UserFragmentAdmin extends Fragment {
    public UserFragmentAdmin(){
        // require a empty public constructor
    }

    private ArrayList<User> listaUtilizadores;
    private UserListAdapterAdmin userListAdapterAdmin;
    private RecyclerView userRecyclerView;
    private AlertDialog adicionarDialogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_admin, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listaUtilizadores = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    if(Objects.equals(documentSnapshot.getString("admin"), "0")){
                        User user = new User(documentSnapshot.getString("username"), documentSnapshot.getString("birth"), documentSnapshot.getString("email"), documentSnapshot.getLong("distance"), documentSnapshot.getLong("bikeid"), documentSnapshot.getLong("points"));
                        listaUtilizadores.add(user);
                    }
                }

                // Create and set adapter for RecyclerView
                userRecyclerView = container.findViewById(R.id.userFragment);
                userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                userListAdapterAdmin = new UserListAdapterAdmin(listaUtilizadores, getActivity());
                userRecyclerView.setAdapter(userListAdapterAdmin);
            }
        });

        return view;
    }

}
