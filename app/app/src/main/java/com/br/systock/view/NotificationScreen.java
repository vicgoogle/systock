package com.br.systock.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.systock.R;
import com.br.systock.model.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationScreen extends AppCompatActivity {


    private static final String Notifications = "Notifications";

    private String userId;
    private FirebaseAuth mAuth;

    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    ArrayList<Notifications> notification_list;
    NotificationsAdapter notification_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.screen_notification);

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler1);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        notification_list = new ArrayList<Notifications>();

        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(userId).child("Notifications");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Notifications b = dataSnapshot1.getValue(Notifications.class);
                    notification_list.add(b);
                }
                notification_list_adapter = new NotificationsAdapter(NotificationScreen.this, notification_list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(notification_list_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), R.string.oops, Toast.LENGTH_SHORT);
            }

        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    }


