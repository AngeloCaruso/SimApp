package com.example.ademsimapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ademsimapp.ui.main.PlotData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    ArrayList<Device> devices;
    int layoutType;
    FirebaseDatabase firebaseDatabase;

    public DeviceAdapter(Context context, ArrayList<Device> devices, int layoutType) {
        this.devices = devices;
        this.layoutType = layoutType;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (this.layoutType == 0){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.devices_card, viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unused_devices_card, viewGroup, false);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int i) {
        final Device device = devices.get(i);
        holder.name.setText(device.getName());
        holder.type.setText(device.getType());
        holder.code.setText(device.getCode());
        if(this.layoutType == 0){
            holder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = firebaseDatabase.getReference("devices");
                    ref.orderByChild("code").equalTo(device.getCode()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dev : dataSnapshot.getChildren()){
                                dev.getRef().child("data").removeValue();
                                for (int i = 0; i < 10; i++){
                                    PlotData plot = new PlotData(i, new Random().nextInt(30));
                                    dev.getRef().child("data").push().setValue(plot);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder{

        private TextView name, type, code;
        private Button btnStart, btnStop;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txtDevName);
            type = (TextView)itemView.findViewById(R.id.txtDevCode);
            code = (TextView)itemView.findViewById(R.id.txtDevType);
            btnStart = (Button)itemView.findViewById(R.id.btnInit);
            btnStop = (Button)itemView.findViewById(R.id.btnStop);
        }
    }

}
