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

public class UnusedDevFragment extends Fragment {
    private RecyclerView unusedDevList;
    private ArrayList<Device> devices;
    private DeviceAdapter deviceAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unused_devices_list, container, false);
        unusedDevList = (RecyclerView)view.findViewById(R.id.unusedDevRecycler);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference devicesRef = database.getReference("devices");

        devicesRef.orderByChild("active").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                devices = new ArrayList<>();
                for(DataSnapshot actualData : dataSnapshot.getChildren()){
                    String name = actualData.child("name").getValue().toString();
                    String type = actualData.child("type").getValue().toString();
                    String code = actualData.child("code").getValue().toString();
                    devices.add(new Device(name, type, code));
                }
                deviceAdapter = new DeviceAdapter(getContext(), devices, 1);
                layoutManager = new LinearLayoutManager(getContext());
                unusedDevList.setLayoutManager(layoutManager);
                unusedDevList.setAdapter(deviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
