package com.example.a2048mult.Control.wifi.tryy;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientClass extends ConnectionTypeClass {
    String hostAdd;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientClass(InetAddress hostAddress) {
        hostAdd = hostAddress.getHostAddress();
        socket = new Socket();

    }

    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();


        ClientThread clientThread = new ClientThread(socket, inputStream);
        executor.execute(clientThread);

    }
}