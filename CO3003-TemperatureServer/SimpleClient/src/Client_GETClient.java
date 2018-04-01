/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 *  @author DuongThaiMinh
 */

public class Client_GETClient {
    static final String CLOUD = "139.162.45.207";
    static final String LOCALHOST = "localhost";

    static void connect(String host) throws Exception {
        int port = 10000;

        Socket socket = new Socket(host, port);
        socket.setSoTimeout(1000);
        if (socket.isConnected()) {
            System.out.println("Connection Success!");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            if (new Random().nextBoolean()) {
                System.out.println("print first");
                Thread.sleep(500);
            } else {
                Thread.sleep(500);
                System.out.println("sleep first");
            }

            os.write("GET hello_world".getBytes());
            os.flush();
            System.out.println("flush data finished");

            while (is.available() == 0) ;
            Scanner scanner = new Scanner(is);
            String line = scanner.nextLine();
            System.out.println("Receive:");
            System.out.println(line);
            System.out.println("\\");
        }
        socket.close();
        System.out.println("Connection finish");
    }

    public static void main(String[] args) throws Exception {
        int Time = 1;
        while (Time-- > 0) {
            System.out.println("Countdown: " + Time);
            new Thread(() -> {
                try {
                    connect(LOCALHOST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
