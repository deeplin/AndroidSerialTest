package com.david;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.david.core.util.ContextUtil;
import com.david.core.util.FileUtil;
import com.david.core.util.LoggerUtil;

import android_serialport_api.SerialControl;
import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    private final int EXECUTE_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextUtil.initialize(getApplication());

        SerialPort.loadLibrary();

//        testOutput();

        send();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void testOutput() {
        try {
            String outputString = FileUtil.readTextFileFromAssets("SerialOutput.txt");

            String[] outputArray = outputString.split(" ");

            Log.e("David", "Output length: " + outputArray.length);

//            if (BuildConfig.DEBUG && outputArray.length != 1024 * EXECUTE_TIME ) {
//                throw new AssertionError("Assertion failed");
//            }

            int previous = -1;
            for (int index = 0; index < outputArray.length; index++) {
                String tmp = outputArray[index];

                if(tmp.trim().length() == 0){
                    continue;
                }
//
                int data = Integer.parseInt(tmp.trim(), 16);
                if (data != (previous + 1) % 256) {
                    LoggerUtil.se(index + " " + tmp + " " + data + " " + previous);
                }
                if(index % 1024 == 0){
                    LoggerUtil.w(index + " " + (index / 1024)+" " + tmp + " " + data + " " + previous);
                }

                previous = data;
            }
        } catch (Exception e) {
            LoggerUtil.e(e);
        }
    }

    private void send() {
        long start = System.currentTimeMillis();
        SerialControl serialControl = new SerialControl();
        try {
            serialControl.open("/dev/ttyS2", 115200);
            byte[] buffer = new byte[1024];
            for (int index = 0; index < buffer.length; index++) {
                buffer[index] = (byte) index;
            }

            for (int index = 0; index < EXECUTE_TIME; index++) {
                serialControl.write(buffer, buffer.length);
            }
        } catch (Exception e) {
            LoggerUtil.e(e);
        }
        long end = System.currentTimeMillis();
        Log.e("David", "Time " + (end - start));

        serialControl.close();
    }
}