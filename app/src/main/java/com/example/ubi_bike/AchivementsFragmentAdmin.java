package com.example.ubi_bike;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AchivementsFragmentAdmin extends Fragment {

    public AchivementsFragmentAdmin(){
        // require a empty public constructor
    }

    private ArrayList<Achievement> achievementsList;
    private RecyclerView achievementRecyclerView;
    private AchievementListAdapterAdmin listaAchievementsAdapter;
    private AlertDialog adicionarDialogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_achievements_admin, container, false);

        Button addButton = view.findViewById(R.id.botaoAdicionarAchievement);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarAchievement();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("achievments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                achievementsList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Achievement achievement = new Achievement(documentSnapshot.getId(), documentSnapshot.getString("description"), documentSnapshot.getString("points"));
                    achievementsList.add(achievement);
                }

                // Create and set adapter for RecyclerView
                achievementRecyclerView = container.findViewById(R.id.achievement);
                achievementRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                listaAchievementsAdapter = new AchievementListAdapterAdmin(achievementsList, getActivity());
                achievementRecyclerView.setAdapter(listaAchievementsAdapter);
            }
        });

        return view;
    }

    private void adicionarAchievement() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_achievement, null);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Achievement");

        EditText Nome = dialogView.findViewById(R.id.textoNome);
        EditText Pontos = dialogView.findViewById(R.id.textoPontos);
        EditText Descricao = dialogView.findViewById(R.id.textoDescricao);
        Button botaoConfirmar = dialogView.findViewById(R.id.botaoConfirmar);
        Button botaoCancelar = dialogView.findViewById(R.id.botaoCancelar);

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = Nome.getText().toString().trim();
                String pontos = Pontos.getText().toString().trim();
                String descricao = Descricao.getText().toString().trim();

                if (nome.isEmpty() || pontos.isEmpty() || descricao.isEmpty()) {
                    Toast.makeText(getActivity(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    Achievement novoAchievement = new Achievement(nome, descricao, pontos);
                    achievementsList.add(novoAchievement);
                    adicionarAchievementFirestore(novoAchievement, nome);
                    adicionarDialogo.dismiss();

                    listaAchievementsAdapter.notifyDataSetChanged();
                }
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDialogo.dismiss();
            }
        });

        adicionarDialogo = builder.create();
        adicionarDialogo.show();
    }

    private void adicionarAchievementFirestore(Achievement achievement, String nome) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("achievments").document(nome)
                .set(achievement)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Achievement adicionado.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
