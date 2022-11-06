package com.example.vibeme.find_friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibeme.R;
import com.example.vibeme.vibers_frag.vibers;
import com.example.vibeme.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
**/
import java.util.ArrayList;

public class friends extends AppCompatActivity {


   DatabaseReference reference;
    View view;
    RecyclerView recyclerView1;
    ArrayList<vibers> list;
    myAdapter adapter;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);



        mToolbar =  findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        reference= FirebaseDatabase.getInstance().getReference().child("vibeme").child("Users");
        reference.keepSynced(true);


        list=new ArrayList<vibers>();
        recyclerView1 =(RecyclerView)findViewById(R.id.recy);
        adapter=new myAdapter(friends.this,list);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));




        recyclerView1.setLayoutManager(new LinearLayoutManager(friends.this));


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    vibers p=dataSnapshot1.getValue(vibers.class);
                    if(!(p.getId().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())))
                    {
                        list.add(p);
                    }

                }
                adapter=new myAdapter(friends.this,list);

                recyclerView1.setAdapter(adapter);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(friends.this, "Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        sendusertoMain();
    }

    private void sendusertoMain() {
        Intent mainIntent = new Intent(friends.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
