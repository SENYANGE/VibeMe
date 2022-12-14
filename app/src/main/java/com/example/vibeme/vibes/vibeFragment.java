package com.example.vibeme.vibes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibeme.R;

import com.example.vibeme.vibers_frag.vibers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class vibeFragment extends Fragment {

    RecyclerView recyclerView_chat;
    View view;

    DatabaseReference reference_user,reference_contact,reference_chat;

    FirebaseAuth auth;

    String current_user_id,Users_id;
    String userImage="default_image";
    public vibeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_vibe, container, false);
        recyclerView_chat=(RecyclerView)view.findViewById(R.id.rec_chat);

        auth=FirebaseAuth.getInstance();
        current_user_id=auth.getCurrentUser().getUid();
        reference_chat= FirebaseDatabase.getInstance().getReference().child("vibeme").child("Contacts").child(current_user_id);
        reference_user= FirebaseDatabase.getInstance().getReference().child("vibeme").child("Users");


        recyclerView_chat.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<vibers>()
                        .setQuery(reference_chat, vibers.class)
                        .build();

        FirebaseRecyclerAdapter<vibers,myviewHolder> adapter =new FirebaseRecyclerAdapter<vibers, myviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final myviewHolder holder, int i, @NonNull vibers contacts) {
                Users_id=getRef(i).getKey();
                reference_user.child(Users_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild("image")) {

                                userImage = dataSnapshot.child("image").getValue().toString();
                                Picasso.get().load(userImage).placeholder(R.drawable.profile).into(holder.profileImage);

                            }


                            final String profileName = dataSnapshot.child("name").getValue().toString();
                            String profileStatus = dataSnapshot.child("Status").getValue().toString();

                            holder.userName.setText(profileName);
                            //   holder.userStatus.setText("last seen "+"Date "+" Time");
                            if (dataSnapshot.child("userstate").hasChild("State")) {
                                String state = dataSnapshot.child("userstate").child("State").getValue().toString();
                                String date = dataSnapshot.child("userstate").child("date").getValue().toString();
                                String time = dataSnapshot.child("userstate").child("Time").getValue().toString();

                                if (state.equals("Online")) {
                                    holder.userStatus.setText("online");
                                    holder.onlineIcon.setVisibility(View.VISIBLE);

                                } else if (state.equals("offline")) {
                                    holder.userStatus.setText("last seen : " + date + "   "+ time);
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);

                                }

                            }





                            if (dataSnapshot.child("userstate").hasChild("State")) {
                                String state = dataSnapshot.child("userstate").child("State").getValue().toString();
                                String date = dataSnapshot.child("userstate").child("date").getValue().toString();
                                String time = dataSnapshot.child("userstate").child("Time").getValue().toString();
                                if (state.equals("Online")) {
                                    holder.onlineIcon.setVisibility(View.VISIBLE);


                                } else if (state.equals("offline")) {
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
                                    holder.userStatus.setText("last seen "+date+""+ time);

                                }
                            }
                            else
                            {
                                holder.onlineIcon.setVisibility(View.INVISIBLE);
                            }




                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getContext(), vibeActivity.class);
                                    if (dataSnapshot.hasChild("image")) {
                                        userImage = dataSnapshot.child("image").getValue().toString();
                                        intent.putExtra("image",userImage);
                                        intent.putExtra("userid",Users_id);
                                        intent.putExtra("name_mm",profileName);

                                    }
                                    else {
                                        intent.putExtra("userid", Users_id);
                                        intent.putExtra("name_mm", profileName);
                                        intent.putExtra("image","default");

                                    }
                                    startActivity(intent);
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public myviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.findfriend, null, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutView.setLayoutParams(lp);
                vibeFragment.myviewHolder rcv = new vibeFragment.myviewHolder(layoutView);
                return rcv;
            }
        };

        recyclerView_chat.setAdapter(adapter);
        adapter.startListening();

    }


    public static class myviewHolder extends RecyclerView.ViewHolder {
        TextView userName, userStatus;
        ImageView profileImage;
        ImageView onlineIcon;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.profile_name);
            userStatus = itemView.findViewById(R.id.profile_statue);
            profileImage = itemView.findViewById(R.id.rect);
            onlineIcon = itemView.findViewById(R.id.onlineornot);
        }
    }
}
