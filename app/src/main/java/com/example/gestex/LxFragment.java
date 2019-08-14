package com.example.gestex;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hootsuite.nachos.chip.Chip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class LxFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    private LinearLayout amine;
    private Button button_lancer;
    private Button button_d;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private String Time;
    int hour ,min;
    private List<String> m = new ArrayList<>();
 //   private  static Boolean verfie = false;
    NotificationManager mNotificationManager;


    public LxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_lx, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        button_d = v.findViewById(R.id.heur_btn);
        button_lancer = v.findViewById(R.id.button2);
        textView = v.findViewById(R.id.heur);

        final String user_id = firebaseAuth.getUid();


        firebaseFirestore.collection("cm").document(user_id).collection("notification").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(!queryDocumentSnapshots.isEmpty()){

                    if(queryDocumentSnapshots.size()>=2){


                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                            String x =  doc.getDocument().getString("user_id");
                            m.add(x);

                        }

                      button_d.setVisibility(View.VISIBLE);
                        button_lancer.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);

                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(getActivity(), "notify_001");
                      //  Intent ii = new Intent(getActivity(), MainActivity.class);
                      //  PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, ii, 0);

                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.bigText("");
                        bigText.setBigContentTitle("Tous les Enseignants sont prét");
                        bigText.setSummaryText("");

                     //   mBuilder.setContentIntent(pendingIntent);
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                        mBuilder.setContentTitle("");
                        mBuilder.setContentText("");
                        mBuilder.setPriority(Notification.PRIORITY_MAX);
                        mBuilder.setStyle(bigText);

                        mNotificationManager =
                                (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

                        // For Android 8 or higher You have to Create Channel_id
                        if (Build.VERSION.SDK_INT >= 26) {

                            String channelId = "YOUR_CHANNEL_ID";
                            NotificationChannel channel = null;

                            channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
                            mNotificationManager.createNotificationChannel(channel);
                            mBuilder.setChannelId(channelId);
                        }

                        mNotificationManager.notify(0, mBuilder.build());



                    }
                }

            }
        });



        button_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog();
            }
        });


        button_lancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(Time)){

                    HashMap<String,Object> map = new HashMap<>();

                    map.put("time",Time);
                    map.put("hour",Integer.toString(hour));
                    map.put("minute",Integer.toString(min));
               // Toast.makeText(getActivity(), ""+m.size(), Toast.LENGTH_SHORT).show();

                  for(String h : m) {
                      firebaseFirestore.collection("Time").document(h).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {

                              if (task.isSuccessful()) {





                              }

                          }
                      });
                  }
                    Toast.makeText(getActivity(), "temp ajouté", Toast.LENGTH_SHORT).show();
                }






                }


        });



        return v;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Time = "" + hourOfDay + ":" + minute;
        hour = hourOfDay;
        min = minute;


        textView.setText(Time);

    }

    private void showTimePickerDialog() {

        TimePickerDialog datePickerDialog = new TimePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
        datePickerDialog.show();
    }






}
