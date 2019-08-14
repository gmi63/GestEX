package com.example.gestex;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class
TimeFragment extends Fragment {

    TextView timerValue;
    ImageView imgtimer;
    LinearLayout fitone;
    private FirebaseFirestore firebaseFirestore;
    NotificationManager mNotificationManager;
    private static final long START_TIME_IN_MILLIS = 0;
    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private FirebaseAuth firebaseAuth;
    private String user_id;
    private String minute,hour;

    Animation btthree, bttfour, ttbone, ttbtwo, alphagogo;


    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_time, container, false);


        btthree = AnimationUtils.loadAnimation(getActivity(), R.anim.btthree);
        bttfour = AnimationUtils.loadAnimation(getActivity(), R.anim.bttfour);
        ttbone = AnimationUtils.loadAnimation(getActivity(), R.anim.ttbone);
        ttbtwo = AnimationUtils.loadAnimation(getActivity(), R.anim.ttbtwo);
        alphagogo = AnimationUtils.loadAnimation(getActivity(), R.anim.alphagogo);
        firebaseAuth = FirebaseAuth.getInstance();


        firebaseFirestore = FirebaseFirestore.getInstance();
        user_id = firebaseAuth.getUid();

        firebaseFirestore.collection("Time").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {


                if(documentSnapshot.exists()){


                    firebaseFirestore.collection("Time").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(task.isSuccessful()){

                                minute = task.getResult().getString("minute");
                                hour = task.getResult().getString("hour");
                                int m = Integer.parseInt(minute);
                                int h = Integer.parseInt(hour);
                                long min = Long.parseLong(minute) * 60000;
                                long millisInput = Long.parseLong(hour) * 3600000;
                             //   Toast.makeText(getActivity(), ""+h+":"+m, Toast.LENGTH_SHORT).show();

                                h = h*360000;
                                m = m*60000;
                               // int result =h+m;
                                long result = min+millisInput;
                                mTimeLeftInMillis = result;
                              //  Long r=Long.valueOf(result);
                                countDownTimer = new CountDownTimer(result, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        mTimeLeftInMillis = millisUntilFinished;
                                       // updateCountDowntText();
                                        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
                                        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
                                        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

                                        String timeLeft = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
                                        timerValue.setText(timeLeft);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Toast.makeText(getActivity(), "finish", Toast.LENGTH_SHORT).show();
                                        firebaseFirestore.collection("Time").document(user_id).delete();
                                    }
                                }.start();
                                mTimerRunning = true;


                            }


                        }
                    });

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getActivity(), "notify_001");
                    //  Intent ii = new Intent(getActivity(), MainActivity.class);
                    //  PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, ii, 0);

                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                    bigText.bigText("");
                    bigText.setBigContentTitle("l'examen est débuté");
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

                  //  startTimer();


                }

            }
        });


        // import font
        Typeface MLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MMedium.ttf");
        Typeface Vidaloka = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Vidaloka.ttf");



        timerValue = (TextView) v.findViewById(R.id.timerValue);
        fitone = (LinearLayout) v.findViewById(R.id.fitone);
        imgtimer = (ImageView) v.findViewById(R.id.imgtimer);
        timerValue.setTypeface(MMedium);


        // assign animation
        fitone.startAnimation(ttbone);
        timerValue.startAnimation(alphagogo);
        imgtimer.startAnimation(alphagogo);





        return v;
    }

    private void startTimer(){

    }

    private void updateCountDowntText(){

    }







}
