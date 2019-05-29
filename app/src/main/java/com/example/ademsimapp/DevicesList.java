package com.example.ademsimapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DevicesList extends AppCompatActivity {

    private RecyclerView devicesList;
    private ArrayList<Device> devices;
    private DeviceAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String user;
    private Intent in;
    private Button btnInit, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_list);
        in = getIntent();
        user = in.getStringExtra("user");
        System.out.println(user);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("devices");
        devicesList = (RecyclerView)findViewById(R.id.deviceRecycler);
        btnInit = (Button)findViewById(R.id.btnInit);
        btnStop = (Button)findViewById(R.id.btnStop);

        ref.orderByChild("owner").equalTo(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                devices = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String name = data.child("name").getValue().toString();
                    String type = data.child("type").getValue().toString();
                    String code = data.child("code").getValue().toString();
                    devices.add(new Device(name, type, code));
                }
                adapter = new DeviceAdapter(getApplicationContext(), devices, 0);
                layoutManager = new LinearLayoutManager(DevicesList.this);
                devicesList.setLayoutManager(layoutManager);
                devicesList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void startSimulation(View v){
        btnInit.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "Simulation started on ", Toast.LENGTH_SHORT).show();
    }

    public void stopSimulation(View v){
        btnInit.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Simulation stopped", Toast.LENGTH_SHORT).show();
    }

}
