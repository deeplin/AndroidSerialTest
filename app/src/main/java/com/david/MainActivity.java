package com.david;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SerialPort.loadLibrary();

        

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
