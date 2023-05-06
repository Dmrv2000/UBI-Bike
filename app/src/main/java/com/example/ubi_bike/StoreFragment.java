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
import java.util.Collections;
import java.util.Comparator;

public class StoreFragment extends Fragment {

    public StoreFragment(){
        // require a empty public constructor
    }

    private ArrayList<Product> productList;
    private RecyclerView productRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve products from Firestore
        db.collection("store").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                productList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    //Product product = documentSnapshot.toObject(Product.class);
                    Product product = new Product(documentSnapshot.getString("amount"),documentSnapshot.getString("value"),documentSnapshot.getId());
                    productList.add(product);
                    //Log.d("Taf",documentSnapshot.getString("amount"));
                }

                Collections.sort(productList, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return Integer.compare(Integer.valueOf(o1.getValue()), Integer.valueOf(o2.getValue()));
                    }
                });

                // Create and set adapter for RecyclerView
                productRecyclerView = container.findViewById(R.id.achievmets);
                productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ProductListAdapter productListAdapter = new ProductListAdapter(productList);
                productRecyclerView.setAdapter(productListAdapter);
            }
        });

        return inflater.inflate(R.layout.fragment_store, container, false);
    }
}