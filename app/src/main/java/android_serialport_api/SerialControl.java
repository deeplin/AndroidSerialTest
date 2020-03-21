package android_serialport_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialControl {

    private SerialPort serialPort;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;

    public SerialControl() {
    }

    public synchronized void open(String deviceName, int baudrate) throws SecurityException, IOException {
        if (serialPort != null) {
            serialPort.close();
        }
        serialPort = new SerialPort(new File(deviceName), baudrate, 0);
        outputStream = serialPort.getOutputStream();
        inputStream = serialPort.getInputStream();
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    public void write(byte[] request, int length) throws IOException {
        outputStream.write(request, 0, length);
        outputStream.flush();
    }

    public int read(byte[] response, int offset, int length) throws IOException {
        return inputStream.read(response, offset, length);
    }
}