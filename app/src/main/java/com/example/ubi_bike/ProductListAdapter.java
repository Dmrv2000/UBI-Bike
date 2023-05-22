package com.example.ubi_bike;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private final List<Product> productList;

    public ProductListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productAmount.setText("Amount Available: "+ product.getAmountAvailable());
        holder.productValue.setText("Price: "+ product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productAmount;
        public TextView productNameTextView;
        public TextView productValue;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productAmount = itemView.findViewById(R.id.product_qtd);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productValue = itemView.findViewById(R.id.product_val);

        }
    }
}
