package com.example.gestex;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

import static android.os.Build.VERSION_CODES.N;


/**
 * A simple {@link Fragment} subclass.
 */
public class AjouterEnseignantFragment extends Fragment {

    private TextView name;
    private TextView prenom;
    private TextView  email;
    private TextView password;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Button valider;
    private EditText module;
    private RadioGroup radio;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton radioButton;
    private String grade="oui";



    public AjouterEnseignantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ajouter_enseignant, container, false);


        name = v.findViewById(R.id.nom);
        prenom = v.findViewById(R.id.prenom);
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);
        valider = v.findViewById(R.id.btnV);
        module = v.findViewById(R.id.module);
        radio = v.findViewById(R.id.gender);
        r1 = v.findViewById(R.id.radio_female);
        r2 = v.findViewById(R.id.radio_male);










        firebaseAuth  = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String user_id = firebaseAuth.getUid();


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected

                        switch(checkedId) {
                            case R.id.radio_female:
                                grade="oui";
                                break;
                            case R.id.radio_male:
                                grade="non";
                                break;
                        }
                    }
                });


                final String nom_ens = name.getText().toString();
                final String module_ens= module.getText().toString();
                final String prenom_ens= prenom.getText().toString();
                final String email_ens= email.getText().toString();
                final String pass_ens = password.getText().toString();
            //   String grade = radioButton.getText().toString();

                if(!TextUtils.isEmpty(nom_ens)&&!TextUtils.isEmpty(module_ens)&&!TextUtils.isEmpty(prenom_ens)&&!TextUtils.isEmpty(email_ens)&&!TextUtils.isEmpty(pass_ens)){

                    if(grade.equals("oui")){

                        firebaseAuth.createUserWithEmailAndPassword(email_ens,pass_ens).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                final String user_id = firebaseAuth.getUid();

                                HashMap<String, Object> map = new HashMap<>();

                                map.put("name", nom_ens);
                                map.put("nickname",prenom_ens);
                                map.put("email",email_ens);
                                map.put("module",module_ens);
                                map.put("mot de passe ",pass_ens);
                             //   String random = UUID.randomUUID().toString();
                                firebaseFirestore.collection("cm").document(user_id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {


                                            HashMap<String,Object> m = new HashMap<>();

                                            m.put("user_id",user_id);


                                            firebaseFirestore.collection("module").document(module_ens).set(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Toast.makeText(getActivity(), "cm ajoute", Toast.LENGTH_SHORT).show();

                                                }
                                            });





                                        } else {
                                            Toast.makeText(getActivity(), "cm non ajouter", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });



                            }
                        });




                    }

                    //grade non
                    else{
                        firebaseAuth.createUserWithEmailAndPassword(email_ens,pass_ens).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String user_id = firebaseAuth.getUid();

                                HashMap<String, Object> map = new HashMap<>();

                                map.put("name", nom_ens);
                                map.put("nickname",prenom_ens);
                                map.put("email",email_ens);
                                map.put("module",module_ens);
                                map.put("mot de passe ",pass_ens);
                                //   String random = UUID.randomUUID().toString();
                                firebaseFirestore.collection("ens").document(user_id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(getActivity(), "ens Ajouté", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "ens non Ajouté", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });



                            }
                        });
                    }



                }



            }
        });

















        return v;
    }

}

//if(TextUtils.isEmpty())

//   int radioId = radio.getCheckedRadioButtonId();
//    radioButton  = v.findViewById(radioId);
//    String grade="oui";