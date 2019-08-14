package com.example.gestex;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class GxFragment extends Fragment {


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth ;
    private Switch aSwitch;
    NotificationManager mNotificationManager;


    public GxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_gx, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        final String user_id = firebaseAuth.getUid();


 firebaseFirestore.collection("Time").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
     @Override
     public void onComplete(@NonNull Task<DocumentSnapshot> task) {

         if(!task.getResult().exists()){
             firebaseFirestore.collection("ens").document(user_id)
                     .collection("notification").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                     if(!queryDocumentSnapshots.isEmpty()){

                         firebaseFirestore.collection("ens").document(user_id)
                                 .collection("notification").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                 String date = task.getResult().getString("Date");
                                 String time = task.getResult().getString("Time");
                                 String salle = task.getResult().getString("salle");



                                 NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity(), "notify_001");
                                 //  Intent ii = new Intent(getActivity(), MainActivity.class);
                                 //   PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, ii, 0);

                                 NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                                 bigText.bigText("");
                                 bigText.setBigContentTitle("la date : "+date+" le temp : \n"+time+" la salle : \n"+salle+"\n");
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
                         }) ;

                     }



                 }
             });


         }

     }
 });







        aSwitch = v.findViewById(R.id.check);

          aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                  if(aSwitch.isChecked()){




                      firebaseFirestore.collection("ens").document(user_id)
                              .collection("notification").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                              if(task.getResult().exists()){

                                  if(task.isSuccessful()){

                                      String module = task.getResult().getString("Module");


                      firebaseFirestore.collection("module").document(module).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                              if(task.getResult().exists()){


                                  if(task.isSuccessful()){

                                      String cm_id = task.getResult().getString("user_id");

                                      HashMap<String,Object> m = new HashMap<>();
                                      m.put("user_id",user_id);


                      firebaseFirestore.collection("cm").document(cm_id).collection("notification").document(user_id).set(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {


                           //   firebaseFirestore.collection("ens").document(user_id).collection("notification").document("1").delete();
                              Toast.makeText(getActivity(), "Prét", Toast.LENGTH_SHORT).show();

                          }
                      });




                                  }



                              }




                          }
                      });


                                  }



                              }


                          }
                      });




                  }

              }
          });














        return v;
    }

}
