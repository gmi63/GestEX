package com.example.gestex;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsulterFragment extends Fragment {
    private List<etudiant>etudiants;
    private AdapterEtudiant adapterEtudiant;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String salle;
    private Button button;



    public ConsulterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_consulter, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String user_id = firebaseAuth.getUid();
        button = v.findViewById(R.id.button3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "envoyer", Toast.LENGTH_SHORT).show();
            }
        });



        recyclerView = v.findViewById(R.id.recycler_view);
        etudiants = new ArrayList<>();
        adapterEtudiant = new AdapterEtudiant(getActivity(),etudiants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //getApplicationContext  -> this
        recyclerView.setAdapter(adapterEtudiant);

        etudiants.clear();


        firebaseFirestore.collection("ens").document(user_id).collection("notification")
                .document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().exists()){

                    if(task.isSuccessful()){

                        salle = task.getResult().getString("salle");

                      //  Toast.makeText(getActivity(), ""+salle, Toast.LENGTH_SHORT).show();

                        firebaseFirestore.collection("etudiant").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {

                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {


                                    if (doc.getType() == DocumentChange.Type.ADDED) {

                                        String user_id = doc.getDocument().getId();

                                        String s = doc.getDocument().getString("salle");

                                        if(salle.equals(s)){
                                            etudiant etudiant = doc.getDocument().toObject(etudiant.class).withId(user_id);
                                            etudiants.add(etudiant);}

                                        adapterEtudiant.notifyDataSetChanged();

                                    }


                                }


                            }
                        });


                    }
                }

            }
        });






        return v;
    }

}
