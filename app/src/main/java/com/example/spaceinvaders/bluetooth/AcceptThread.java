package com.example.spaceinvaders.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
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

public class AcceptThread extends Thread {
    private final BluetoothServerSocket serverSocket;
    private final Context context;

    public AcceptThread(Context context, BluetoothAdapter bluetoothAdapter) {
        this.context = context;
        BluetoothServerSocket tmp=null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            try {
                String NAME = "SpaceInvaders";
                UUID MY_UUID = UUID.fromString("f3c74f47-1d38-49ed-8bbc-0369b3eb277c");
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serverSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
                break;
            }

            if (socket != null) {
                ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Devices are connected",Toast.LENGTH_LONG).show());
                // TODO transfer data, what happens if host and search are working simultaneously, should adapter.cancelDiscovery be called or kill thread second option
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TransferService.setSocket(context,socket);
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("mode", "HOST");
                context.startActivity(intent);
                break;
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
