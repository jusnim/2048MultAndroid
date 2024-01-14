package com.example.a2048mult.Control.wifi.tryy;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread {


    private Socket socket;
    private final InputStream inputStream;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private int bytes;

    public ServerThread(Socket socket, InputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }


    public void updateSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];

        while (socket != null) {
            try {
                bytes = inputStream.read(buffer);
                if (bytes > 0) {
                    Runnable readInput = () -> {
                        String tempMSG = new String(buffer, 0, bytes);
                    };
                    handler.post(readInput);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
