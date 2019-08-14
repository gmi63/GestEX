package com.example.gestex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.List;

public class AdapterE extends RecyclerView.Adapter<AdapterE.ViewHolder> {

    private Context context;
    private List<etudiant> etudiants;
    private FirebaseFirestore firebaseFirestore;


    public AdapterE(Context context,List<etudiant> etudiants){

        this.context = context;
        this.etudiants = etudiants;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item1, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        String name = etudiants.get(i).getName();
        String nickname = etudiants.get(i).getNickname();
        viewHolder.textView.setText(nickname+" "+name);
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.name22);

        }
    }
}













