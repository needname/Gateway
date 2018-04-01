/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        try {
            Socket clientSocket = new Socket(host, port);

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            System.out.println("Connected");
            Thread.sleep(1000);
            String message = "Hello Server";
            os.write(message.getBytes());
            os.write("\n".getBytes());
            os.flush();

            while(is.available()==0);
            Scanner scanner = new Scanner(is);
            String line = scanner.nextLine();
//            int x = scanner.nextInt();
//            String st = scanner.next();

            System.out.println(line);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
