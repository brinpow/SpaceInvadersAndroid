package com.example.spaceinvaders.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TransferService {
    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;

    public static void setSocket(Context context, BluetoothSocket socket){
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
            ((AppCompatActivity)context).finish();
            e.printStackTrace();
        }
    }

    public static int read(Context context){
        byte[] buffer = new byte[4];
        try {
            inputStream.read(buffer,0, 4);
        } catch (IOException e) {
            ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
            ((AppCompatActivity)context).finish();
            e.printStackTrace();
        }
        return ByteBuffer.wrap(buffer).getInt();
    }

    public static void write(Context context, byte[] bytes){
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            ((AppCompatActivity)context).runOnUiThread(()-> Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show());
            ((AppCompatActivity)context).finish();
            e.printStackTrace();
        }
    }


}
