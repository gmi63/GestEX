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

public class AdapterEtudiant extends RecyclerView.Adapter<AdapterEtudiant.ViewHolder> {

    private Context context;
    private List<etudiant> etudiants;
    private FirebaseFirestore firebaseFirestore;


    public AdapterEtudiant(Context context,List<etudiant> etudiants){

        this.context = context;
        this.etudiants = etudiants;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_e, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        String name = etudiants.get(i).getName();
        String nickname = etudiants.get(i).getNickname();
        viewHolder.textView.setText(nickname+" "+name);
        final HashMap<String,Object> a = new HashMap<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    String user_id = etudiants.get(i).userId;


                    firebaseFirestore.collection("e").document(user_id).set(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            Toast.makeText(context, " etudiant selectioné", Toast.LENGTH_SHORT).show();

                        }
                    });






                }
                else{
                    String user_id = etudiants.get(i).userId;


                    firebaseFirestore.collection("e").document(user_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(context, " etudiant non selectioné ", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View layout;
        private Switch aSwitch;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.name_1);
            layout = itemView.findViewById(R.id.layout);
            aSwitch = itemView.findViewById(R.id.swith_e);

        }
    }
}



/*public class AdapterContacts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<People> items;

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    //  public List<String> a = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, People obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterContacts(Context context, List<People> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView email;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            email = (TextView) v.findViewById(R.id.email);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_contacts, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            People p = items.get(position);
            view.name.setText(p.name);
            view.email.setText(p.email);
            //
            //   Toast.makeText(ctx, , Toast.LENGTH_SHORT).show();
            // view.image.s(p.image);
            //
            //   Tools.displayImageRound(ctx, view.image, p.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {

                        //a.add(p.UserId);
                        // Toast.makeText(ctx, ""+items.get(position).image, Toast.LENGTH_SHORT).show();
                        // a.add(items.get(position).image);
                        //  envoyerList.setEns(a);

                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}





*/









