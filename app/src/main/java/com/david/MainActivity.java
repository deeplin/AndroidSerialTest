package com.david;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.david.core.util.LoggerUtil;

import android_serialport_api.SerialControl;
import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SerialPort.loadLibrary();

        SerialControl serialControl = new SerialControl();
        try {
            serialControl.open("/dev/ttyS2", 9600);

            byte[] buffer = new byte[256];
            for (int index = 0; index < buffer.length; index++) {
                buffer[index] = (byte) index;
            }

            for (int index = 0; index < 10; index++) {
                serialControl.write(buffer, buffer.length);
            }
        } catch (Exception e) {
            LoggerUtil.e(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
