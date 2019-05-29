package com.example.ademsimapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ademsimapp.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference devicesRef = database.getReference("devices");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = letters[new Random().nextInt(26)] + numbers[new Random().nextInt(10)] +
                        letters[new Random().nextInt(26)] + numbers[new Random().nextInt(10)];
                //Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                Device newDevice = new Device("default", "unsigned", code);
                newDevice.setActive(false);
                devicesRef.push().setValue(newDevice);
            }
        });
    }
}