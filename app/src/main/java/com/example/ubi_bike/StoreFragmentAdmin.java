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
import java.util.Comparator;

public class StoreFragmentAdmin extends Fragment {

    public StoreFragmentAdmin(){
        // require a empty public constructor
    }

    private ArrayList<Product> listaProdutos;
    private ProductListAdapterAdmin listaProdutosAdapter;
    private RecyclerView produtoRecyclerView;
    private AlertDialog adicionarDialogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_admin, container, false);

        Button addButton = view.findViewById(R.id.botaoAdicionarProduto);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarProduto();
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("store").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listaProdutos = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Product product = new Product(documentSnapshot.getString("amount"), documentSnapshot.getString("price"),documentSnapshot.getId());
                    listaProdutos.add(product);
                }

                listaProdutos.sort(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return Integer.compare(Integer.parseInt(o1.getPrice()), Integer.parseInt(o2.getPrice()));
                    }
                });

                // Create and set adapter for RecyclerView
                produtoRecyclerView = container.findViewById(R.id.storeFragment);
                produtoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                listaProdutosAdapter = new ProductListAdapterAdmin(listaProdutos, getActivity());
                produtoRecyclerView.setAdapter(listaProdutosAdapter);
            }
        });

        return view;
    }

    private void adicionarProduto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_product, null);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Produto");

        EditText Nome = dialogView.findViewById(R.id.textoNome);
        EditText Quantidade = dialogView.findViewById(R.id.textoQuantidade);
        EditText Preco = dialogView.findViewById(R.id.textoPreco);
        Button botaoConfirmar = dialogView.findViewById(R.id.botaoConfirmar);
        Button botaoCancelar = dialogView.findViewById(R.id.botaoCancelar);

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = Nome.getText().toString().trim();
                String quantidade = Quantidade.getText().toString().trim();
                String preco = Preco.getText().toString().trim();

                if (nome.isEmpty() || quantidade.isEmpty() || preco.isEmpty()) {
                    Toast.makeText(getActivity(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                } else {
                    Product novoProduto = new Product(quantidade, preco, nome);
                    listaProdutos.add(novoProduto);
                    adicionarProdutoFirestore(novoProduto, nome);
                    adicionarDialogo.dismiss();
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

    private void adicionarProdutoFirestore(Product produto, String nome) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("store").document(nome)
                .set(produto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Produto adicionado.", Toast.LENGTH_SHORT).show();
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