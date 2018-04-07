package Gateway;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TCPGetID implements Runnable {

    private InetAddress host;

    private int port;

    private String requestID = "REGISTER_GATEWAY";

    public static String messRev;

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
            }
            Scanner scanner = new Scanner(inFromServer);
            messRev = scanner.nextLine();
            Gateway.id = messRev;
            System.out.println("FROM SERVER: " + host.toString());

            //StringTokenizer itr = new StringTokenizer(messRev);
            //if(itr.hasMoreTokens()){
             //   if(itr.nextToken().equals("REGISTER_GATEWAY")) {
             //       Gateway.id = itr.nextToken();
             //       System.out.println(Gateway.id);
             //   }
             //   else{
             //       Gateway.id = "no_id";
             //       System.out.println("FROM SERVER: " + messRev);
             //   }
            //}
            getID.close();
        }
        catch(Exception e){
            messRev = e.toString();
        }
    }
}
