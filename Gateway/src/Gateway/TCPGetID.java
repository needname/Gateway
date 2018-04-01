package Gateway;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TCPGetID implements Runnable {

    InetAddress host;

    int port;

    String requestID = "REQUEST_GATEWAY";

    String messRev;

    TCPGetID(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket getID = new Socket(host, port);
            DataOutputStream sendRequest = new DataOutputStream(getID.getOutputStream());
            InputStream inFromServer = getID.getInputStream();

            sendRequest.writeBytes(requestID);
            sendRequest.flush();

            while(inFromServer.available() == 0){
                //System.out.print(".");
            }
            Scanner scanner = new Scanner(inFromServer);
            //messRev = scanner.nextLine();
            messRev = "REQUEST_GATEWAY 1234";
            //System.out.println("FROM SERVER: " + messRev);

            StringTokenizer itr = new StringTokenizer(messRev);
            if(itr.hasMoreTokens()){
                if(itr.nextToken().equals("REQUEST_GATEWAY")) {
                    Gateway.id = Integer.parseInt(itr.nextToken());
                    System.out.println(Gateway.id);
                }
                else{
                    Gateway.id = -1;
                }
            }
            getID.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
