package com.example.gestex;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DfexFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private Button btn;
    private View v;
    private String Time;
    private TextView time;

    public DfexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_dfex, container, false);
        // Inflate the layout for this fragment
        btn = v.findViewById(R.id.h_btn);
        time = v.findViewById(R.id.h_exam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog();


            }
        });


    return v;
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

}

