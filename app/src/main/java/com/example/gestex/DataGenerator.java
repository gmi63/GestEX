
package com.example.gestex;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;


@SuppressWarnings("ResourceType")
public class  DataGenerator  {
    private FirebaseFirestore firebaseFirestore;
    List<People> a = new ArrayList<>();
    List<People> items = new ArrayList<>();

 /*   public List<People> ens (final Context c,final List<People> a) {
        // String user_id = doc.getDocument().getId();
 return a;

    }*/

    public  List<People> getPeopleData(final Context ctx) {


        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       // String user_id = firebaseAuth.getUid();
      //  Toast.makeText(ctx, ""+user_id, Toast.LENGTH_SHORT).show();


       a.clear();

        firebaseFirestore.collection("ens").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot DocumentSnapshots, FirebaseFirestoreException e) {



                if (e!=null){
                 //   Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();

                    Log.d(TAG,"Amine:"+e.getMessage());
                    return;
                }

            //    Toast.makeText(ctx, "for", Toast.LENGTH_SHORT).show();
                for (DocumentChange doc : DocumentSnapshots.getDocumentChanges()) {


                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        String user_id = doc.getDocument().getId();

                 //

                        People users = doc.getDocument().toObject(People.class).withId(user_id);
                    //    Toast.makeText(ctx, " User id = "+user_id, Toast.LENGTH_SHORT).show();


                          a.add(users);
                    //


                    }}

                TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
                //String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
              //  Toast.makeText(ctx, " "+a.size(), Toast.LENGTH_SHORT).show();

                for (int i = 0; i <a.size(); i++) {

                    People obj = new People();
                    obj.image = a.get(i).userId;
                    obj.name = a.get(i).getName();
                    obj.email = a.get(i).getEmail();
                    obj.imageDrw = ctx.getResources().getDrawable(drw_arr.getResourceId(0, -1));
                    items.add(obj);
                }
               // Collections.shuffle(items);









            }
        });
        return items;



    }

}








//   ens(ctx,p);
       /* ite.set(0,ez).setEmail("amin90667@gmail.com");
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        ite.get(0).setImage(drw_arr.getResourceId(0, -1));
        ite.get(0).setImageDrw(ctx.getResources().getDrawable(ite.get(0).getImage()));*/
























/*


package com.example.gestex;

import android.content.Context;
import android.content.res.TypedArray;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@SuppressWarnings("ResourceType")
public class DataGenerator {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<People> items= new ArrayList<>();


    public List ens (final List a) {

        a.clear();
        firebaseFirestore.collection("ens").addSnapshotListener( new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if(!documentSnapshots.isEmpty()){
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {


                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            // String user_id = doc.getDocument().getId();

                            People users = doc.getDocument().toObject(People.class);

                            a.add(users);



                        }


                    }}


            }
        });

        return a;


    }



    public  List<People> getPeopleData(Context ctx) {
        final List<People> items = new ArrayList<>();
        ens(items);
        Collections.shuffle(items);
        return items;
    }

}
*/