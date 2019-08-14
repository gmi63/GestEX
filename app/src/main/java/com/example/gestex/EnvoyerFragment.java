package com.example.gestex;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnvoyerFragment extends Fragment {

    private ChipsInput mChipsInput;
    private List<PeopleChip> items = new ArrayList<>();
    private List<ChipInterface> items_added = new ArrayList<>();
    public List<People> items_people = new ArrayList<>();
    public final static List<String> a = new ArrayList<>();
    private Button button;
    private FirebaseFirestore firebaseFirestore;
    private EditText salle;


    public List<People> getItems_people() {
        return items_people;
    }



    public EnvoyerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_chip_basic, container, false);

        DataGenerator dataGenerator = new DataGenerator();
        salle = v.findViewById(R.id.Salle);




       // items_people.clear();
      //  a.clear();
        items_people = dataGenerator.getPeopleData(v.getContext());
        firebaseFirestore = FirebaseFirestore.getInstance();





        // b = items_people;
        ((ImageButton) v.findViewById(R.id.contacts)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getView().getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.dialog_contacts);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                AdapterContacts _adapter = new AdapterContacts(getContext(), items_people);
                recyclerView.setAdapter(_adapter);
                _adapter.setOnItemClickListener(new AdapterContacts.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, People obj, int position) {
                        mChipsInput.addChip(obj.imageDrw, obj.name, obj.email);

                        a.add(items_people.get(position).image);
                      //  Toast.makeText(getActivity(), "" + items_people.get(position).image, Toast.LENGTH_LONG).show();
                        dialog.hide();
                    }

                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);
            }
        });

        mChipsInput = (ChipsInput) v.findViewById(R.id.chips_input);

        mChipsInput.setFilterableList(items);
        getPeopleChipList();


        // chips listener
        mChipsInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chip, int newSize) {


                    items_added.add(chip);



                    //
                //   Toast.makeText(getActivity(),"id : "+items_people.size(),Toast.LENGTH_LONG).show();
                    //


            }

            @Override
            public void onChipRemoved(ChipInterface chip, int newSize) {
                items_added.remove(chip);
            }

            @Override
            public void onTextChanged(CharSequence text) {
                //Log.e(TAG, "text changed: " + text.toString());
            }
        });


        button = v.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              //  Toast.makeText(getActivity(),""+a.size(),Toast.LENGTH_LONG).show();
                final String m = salle.getText().toString();
                firebaseFirestore.collection("Examen").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                            if(task.isSuccessful()){

                            String  module = task.getResult().getString("Module");
                            String  date = task.getResult().getString("Date");
                            String  time = task.getResult().getString("Heure Examen");
                            String  niveau = task.getResult().getString("Niveau");


                                if(!TextUtils.isEmpty(m)) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("salle", m);
                                    map.put("Time",time);
                                    map.put("Date",date);
                                    map.put("Niveau",niveau);
                                    map.put("Module",module);

                                    for (String s : a) {


                                        firebaseFirestore.collection("ens").document(s).collection("notification")
                                                .document("1").set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {



                                                }

                                            }
                                        });


                                    }
                                    Toast.makeText(getActivity(), "envoyer", Toast.LENGTH_LONG).show();

                                }






                        }

                    }
                });


            }
        });
        a.clear();














       // iniComponent(v,a);

       // Toast.makeText(getActivity(), ""+getItems_people().size(), Toast.LENGTH_SHORT).show();


        return v;
    }
    private void iniComponent(View v,List<People> b) {


    }

    private void getPeopleCipList() {

    }

    public List<PeopleChip> getItems() {
        return items;
    }

    private void getPeopleChipList() {
        Integer id = 0;
        for (People p : items_people) {
            PeopleChip contactChip = new PeopleChip(items_people.get(id).image, p.imageDrw, p.name, p.email);
            // add contact to the list
            items.add(contactChip);
            id++;
        }
        // pass contact list to chips input
        mChipsInput.setFilterableList(items);
    }

    private void dialogCotacts() {

    }


}
