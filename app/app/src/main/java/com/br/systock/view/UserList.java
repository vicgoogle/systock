package com.br.systock.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.systock.R;
import com.br.systock.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {
    DatabaseReference mreference;
    RecyclerView mrecyclerView;
    ArrayList<User> mlist;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user);

        mrecyclerView = (RecyclerView) findViewById(R.id.myRecyclerUser);
        mrecyclerView.setLayoutManager( new LinearLayoutManager(this));
        mlist = new ArrayList<User>();

        mreference = FirebaseDatabase.getInstance().getReference().child("User");
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User r = dataSnapshot1.getValue(User.class);
                    mlist.add(r);
                }
                adapter = new UserAdapter(UserList.this, mlist);
                mrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mrecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserList.this, R.string.oops, Toast.LENGTH_SHORT);
            }
        });
    }
}

