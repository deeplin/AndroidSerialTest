package com.david;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.david.core.util.ContextUtil;
import com.david.core.util.FileUtil;
import com.david.core.util.LoggerUtil;

import android_serialport_api.SerialControl;
import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    private final int EXECUTE_TIME = 10;

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

            if (BuildConfig.DEBUG && outputArray.length != 1024 * EXECUTE_TIME ) {
                throw new AssertionError("Assertion failed");
            }

//            Log.e("David", "Output length: " + outputArray.length);

            int previous = -1;
            for (int index = 0; index < 1024 * EXECUTE_TIME; index++) {
                String tmp = outputArray[index];
//
                int data = Integer.parseInt(outputArray[index].trim(), 16);
                if (data != (previous + 1) % 256) {
                    LoggerUtil.se(index + " " + data + " " + previous);
                }
                LoggerUtil.w(index + " " + tmp + " " + data + " " + previous);
                previous = data;
                Thread.sleep(1);
            }
        } catch (Exception e) {
            LoggerUtil.e(e);
        }
    }

    private void send() {
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
    }
}
