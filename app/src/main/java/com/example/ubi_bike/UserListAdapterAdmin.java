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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserListAdapterAdmin extends RecyclerView.Adapter<UserListAdapterAdmin.UserViewHolder> {

    private final List<User> listaUtilizadores;
    private AlertDialog userDialogo;
    private Context context;

    public UserListAdapterAdmin(List<User> listaUtilizadores, Context context) {
        this.listaUtilizadores = listaUtilizadores;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_admin, parent, false);
        context = parent.getContext();
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = listaUtilizadores.get(position);
        holder.utilizadorNome.setText(user.getName());
        holder.expandUtilizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDadosUtilizador(user);
            }
        });
    }

    private void mostrarDadosUtilizador(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.show_user, null);
        builder.setView(dialogView);

        TextView nome = dialogView.findViewById(R.id.textoNome);
        TextView email = dialogView.findViewById(R.id.textoEmail);
        TextView data = dialogView.findViewById(R.id.textoData);
        TextView distancia = dialogView.findViewById(R.id.textoDistancia);
        TextView idbike = dialogView.findViewById(R.id.textoIdBike);
        TextView pontos = dialogView.findViewById(R.id.textoPontos);

        nome.setText(user.getName());
        email.setText(user.getEmail());
        data.setText(user.getDate());
        distancia.setText(String.valueOf(user.getDistance()));
        idbike.setText(String.valueOf(user.getIdBike()));
        pontos.setText(String.valueOf(user.getPoints()));

        userDialogo = builder.create();
        userDialogo.show();
    }

    @Override
    public int getItemCount() {
        return listaUtilizadores.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView utilizadorNome;
        public CardView expandUtilizador;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            utilizadorNome = itemView.findViewById(R.id.user_name);
            expandUtilizador = itemView.findViewById(R.id.expand_user);
        }
    }
}
