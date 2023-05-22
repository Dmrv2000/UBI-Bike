package com.example.ubi_bike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductListAdapterAdmin extends RecyclerView.Adapter<ProductListAdapterAdmin.ProductViewHolder> {

    private final List<Product> listaProdutos;
    private AlertDialog quantidadeDialogo;
    private Context context;

    public ProductListAdapterAdmin(List<Product> listaProdutos, Context context) {
        this.listaProdutos = listaProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_admin, parent, false);
        context = parent.getContext();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product produto = listaProdutos.get(position);
        holder.produtoNameTextView.setText(produto.getName());
        holder.produtoAmount.setText("Amount Available: "+ produto.getAmountAvailable());
        holder.produtoValue.setText("Pontos: "+ produto.getPrice());

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarQuantidade(produto);
            }
        });

        holder.crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerProduto(produto);
            }
        });
    }

    private void mudarQuantidade(Product produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.change_quantity, null);
        builder.setView(dialogView);

        EditText editarQuantidade = dialogView.findViewById(R.id.editarQuantidade);
        Button botaoConfirmar = dialogView.findViewById(R.id.botaoConfirmar);
        Button botaoCancelar = dialogView.findViewById(R.id.botaoCancelar);

        editarQuantidade.setText(produto.getAmountAvailable());

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novaQuantidade = editarQuantidade.getText().toString().trim();
                if (!novaQuantidade.isEmpty()) {
                    mudarQuantidadeFirestore(produto, novaQuantidade);
                    quantidadeDialogo.dismiss();
                }
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeDialogo.dismiss();
            }
        });
        quantidadeDialogo = builder.create();
        quantidadeDialogo.show();
    }

    private void mudarQuantidadeFirestore(Product produto, String novaQuantidade) {
        int position = listaProdutos.indexOf(produto);
        // Update the Firestore document
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("store").document(produto.getName())
                .update("amount", novaQuantidade)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (position != -1) {
                            produto.setAmountAvailable(novaQuantidade);
                            notifyItemChanged(position);
                            Toast.makeText(context, "Quantidade atualizada.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void removerProduto(Product produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar");
        builder.setMessage("Tem a certeza que quer eliminar este produto?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = listaProdutos.indexOf(produto);
                if (position != -1) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("store").document(produto.getName())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listaProdutos.remove(produto);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, listaProdutos.size());
                                    Toast.makeText(context, "Produto removido com successo.", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView produtoAmount;
        public TextView produtoNameTextView;
        public TextView produtoValue;
        public ImageView plusButton;
        public ImageView crossButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            produtoAmount = itemView.findViewById(R.id.product_qtd);
            produtoNameTextView = itemView.findViewById(R.id.product_name);
            produtoValue = itemView.findViewById(R.id.product_val);
            plusButton = itemView.findViewById(R.id.plusButton);
            crossButton = itemView.findViewById(R.id.crossButton);
        }
    }
}
