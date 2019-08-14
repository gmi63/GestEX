package com.example.gestex;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Half;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;


public class AjouterExamenFragment extends Fragment implements AdapterView.OnItemSelectedListener , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button b;
    private View v;
    private TextView date;
    private TextView time;
    private Button btn;
    private Button valider;
    private String Date;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth fireBaseAuth;
    private String Module;
    private String Time;
    private Spinner semestre;
    private Spinner niveau;
    private String S;
    private String N;
    private Spinner spinner;


    public AjouterExamenFragment() {
        // Required empty public constructor
    }


    @SuppressWarnings("MagicConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ajouter_examen, container, false);

        spinner = v.findViewById(R.id.spinner1);
        date = v.findViewById(R.id.date);
        niveau = v.findViewById(R.id.spinner2);

        btn = v.findViewById(R.id.h_btn);
        time = v.findViewById(R.id.h_exam);
        valider = v.findViewById(R.id.v_btn);

        firebaseFirestore = FirebaseFirestore.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.niveau, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        niveau.setAdapter(adapter1);
        niveau.setOnItemSelectedListener(new Niveau());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.module, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

       // String module=N+"_"+S;



        // Inflate the layout for this fragment
        b = v.findViewById(R.id.btn);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePickerDialog();


            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog();


            }
        });


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(getActivity(), Module + Date + Time, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(Module) && !TextUtils.isEmpty(Date)) {

                    HashMap<String, Object> map = new HashMap<>();

                    map.put("Module", Module);
                    map.put("Date", Date);
                    map.put("Heure Examen", Time);
                    map.put("Niveau",N);
                    String random = UUID.randomUUID().toString();
                    firebaseFirestore.collection("Examen").document("1").set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getActivity(), "Examen Ajouté", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Examen non Ajouté", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });


        return v;
    }

    private void setAdapter(String mz) {
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Module = parent.getItemAtPosition(position).toString();
      //  Toast.makeText(parent.getContext(), Module, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Date = "" + dayOfMonth + "/" + month + "/" + year;
        date.setText(Date);

    }

    private void showTimePickerDialog() {

        TimePickerDialog datePickerDialog = new TimePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
        datePickerDialog.show();
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        Time = "" + hour + ":" + minute;

        time.setText(Time);
    }

     class Niveau implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            N= parent.getItemAtPosition(position).toString();
           // Toast.makeText(v.getContext(), "Your choose :"+N, Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


    }
}