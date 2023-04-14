package com.example.spaceinvaders.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.spaceinvaders.activities.GameActivity;
import com.example.spaceinvaders.managers.GameView;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private final BluetoothAdapter bluetoothAdapter;
    private final BluetoothSocket mSocket;
    private final Context context;

    public ConnectThread(Context context, BluetoothDevice device, BluetoothAdapter bluetoothAdapter) {
        this.context = context;
        this.bluetoothAdapter = bluetoothAdapter;
        BluetoothSocket tmp = null;

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                UUID MY_UUID = UUID.fromString("f3c74f47-1d38-49ed-8bbc-0369b3eb277c");
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSocket = tmp;
    }

    public void run() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
            return;
        }
        bluetoothAdapter.cancelDiscovery();

        try {
            mSocket.connect();
        } catch (IOException connectException) {
            ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
            try {
                mSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
            return;
        }

        ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Devices are connected",Toast.LENGTH_LONG).show());
        TransferService.setSocket(context,mSocket);
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("mode", "CLIENT");
        context.startActivity(intent);
    }
}
