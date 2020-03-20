package com.david;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.david.core.util.ContextUtil;
import com.david.core.util.FileUtil;
import com.david.core.util.LoggerUtil;

import android_serialport_api.SerialControl;
import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {

    private final int EXECUTE_TIME = 10 * 4;

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

            int previous = -1;
            for (int index = 0; index < outputArray.length; index++) {
                String tmp = outputArray[index];
                if (tmp.trim().length() == 0) {
                    continue;
                }
                int data = Integer.parseInt(outputArray[index], 16);
                if (data != (previous + 1) % 256) {
                    LoggerUtil.se(index + " " + data + " " + previous);
                }
                LoggerUtil.w(index + " " + data + " " + previous);
                previous = data;
            }
        } catch (Exception e) {
            LoggerUtil.e(e);
        }
    }

    private void send() {
        SerialControl serialControl = new SerialControl();
        try {
            serialControl.open("/dev/ttyS2", 115200);

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
}
