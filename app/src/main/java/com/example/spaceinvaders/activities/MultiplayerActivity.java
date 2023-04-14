package com.example.spaceinvaders.activities;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH_ADVERTISE;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.bluetooth.AcceptThread;
import com.example.spaceinvaders.databinding.ActivityMultiplayerBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MultiplayerActivity extends AppCompatActivity {
    private final int REQUEST_DISCOVERABLE_BT = 0;
    private final int REQUEST_DISCOVER_BT = 1;
    private final int DISCOVERABLE_TIME = 240;
    private BroadcastReceiver receiver;
    private final String[] requests = {BLUETOOTH_CONNECT, BLUETOOTH_SCAN, BLUETOOTH_ADVERTISE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION} ;


    private boolean checkPermissionsNotGranted(){
        for(String permission: requests){
            if(ActivityCompat.checkSelfPermission(this, permission)!=PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.spaceinvaders.databinding.ActivityMultiplayerBinding binding = ActivityMultiplayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Context context = this;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            Toast.makeText(this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        if(checkPermissionsNotGranted()){
            ActivityCompat.requestPermissions(this, requests,1);
        }
        if(checkPermissionsNotGranted()){
            Toast.makeText(context,"Permissions aren't granted. You can't use this functionality",Toast.LENGTH_LONG).show();
            finish();
        }

        List<BluetoothDevice> devices = new ArrayList<>();
        DevicesViewAdapter adapter = new DevicesViewAdapter(this, devices, bluetoothAdapter);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(!devices.contains(device)){
                        Toast.makeText(context,"Device found",Toast.LENGTH_SHORT).show();
                        devices.add(device);
                    }
                    adapter.notifyDataSetChanged();
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    Toast.makeText(context,"Discovery process finished",Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);

        binding.host.setOnClickListener((v)->{
            Toast.makeText(this,"Turning on Bluetooth",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,DISCOVERABLE_TIME);
            startActivityForResult(intent,REQUEST_DISCOVERABLE_BT);
            AcceptThread acceptThread = new AcceptThread(context, bluetoothAdapter);
            acceptThread.start();
        });

        binding.search.setOnClickListener((v)->{
            if(!bluetoothAdapter.isEnabled()){
                Toast.makeText(this,"Turning on Bluetooth",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,REQUEST_DISCOVER_BT);
            }

            devices.clear();
            adapter.notifyDataSetChanged();
            if(bluetoothAdapter.isDiscovering()){
                Toast.makeText(this,"Discovery cancelled",Toast.LENGTH_SHORT).show();
                bluetoothAdapter.cancelDiscovery();
            }
            Toast.makeText(this,"Searching for other devices",Toast.LENGTH_SHORT).show();

            bluetoothAdapter.startDiscovery();
        });

        RecyclerView devicesView = findViewById(R.id.devicesView);
        devicesView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestedCode, int resultCode, Intent data) {

        super.onActivityResult(requestedCode, resultCode, data);
        switch (requestedCode){
            case REQUEST_DISCOVERABLE_BT:
                System.out.println(resultCode);
                if(resultCode==DISCOVERABLE_TIME){
                    Toast.makeText(this,"Waiting for other player",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Hosting cancelled",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_DISCOVER_BT:
                if(resultCode==RESULT_OK){
                    Toast.makeText(this,"Bluetooth enabled",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Bluetooth denied",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(receiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}