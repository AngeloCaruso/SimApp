package com.example.ademsimapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersFragment extends Fragment implements UserAdapter.OnClickRecyclerListener{
    private RecyclerView usersList;
    private ArrayList<User> users;
    private UserAdapter userAdapter;
    private LinearLayoutManager layoutManager;
    private Intent in;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_list, container, false);
        usersList = (RecyclerView)view.findViewById(R.id.usersRecycler);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("usuarios");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                for(DataSnapshot actualData : dataSnapshot.getChildren()){
                    String id = actualData.child("id").getValue().toString();
                    String username = actualData.child("username").getValue().toString();
                    String email = actualData.child("email").getValue().toString();
                    users.add(new User(id, username, email, R.drawable.default_img));
                }
                userAdapter = new UserAdapter(getContext(), users, UsersFragment.this);
                layoutManager = new LinearLayoutManager(getContext());
                usersList.setLayoutManager(layoutManager);
                usersList.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
    @Override
    public void OnClick(int position) {
        in = new Intent(getContext(), DevicesList.class);
        in.putExtra("user", users.get(position).getId());
        startActivity(in);
    }
}