package com.example.spaceinvaders.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.bluetooth.ConnectThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DevicesViewAdapter extends RecyclerView.Adapter<DevicesViewAdapter.ViewHolder> {
    private final Context context;
    private final List<BluetoothDevice> devices;
    private final BluetoothAdapter bluetoothAdapter;
    static Thread connect;

    public DevicesViewAdapter(Context context, List<BluetoothDevice> devices, BluetoothAdapter bluetoothAdapter) {
        this.context = context;
        this.devices = devices;
        this.bluetoothAdapter = bluetoothAdapter;
    }


    @NonNull
    @Override
    public DevicesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.devices_view_row, parent, false);
        return new DevicesViewAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DevicesViewAdapter.ViewHolder holder, int position) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Bluetooth connection is broken",Toast.LENGTH_LONG).show();
            ((AppCompatActivity)context).finish();
        }
        holder.deviceName.setText(devices.get(position).getName() + "\n"+devices.get(position).getAddress());
        holder.join.setOnClickListener((v -> {
            connect = new ConnectThread(context, devices.get(position),bluetoothAdapter);
            connect.start();
        }));

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView deviceName;
        private final Button join;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            join = itemView.findViewById(R.id.join);
        }
    }
}
