/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleServer {

    public static void main(String[] args) {
        int port = 12345; // <2^32-1
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //
            Socket clientSocket = serverSocket.accept();
            System.out.println("Someone connected");
            //

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            while(is.available()==0){
                System.out.print(".");
                Thread.sleep(500);
            }
            Scanner scanner = new Scanner(is);
            System.out.println("\nCLient send: "+scanner.nextLine());

            os.write("Hello Client\n".getBytes());
            os.write("\n".getBytes());
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
