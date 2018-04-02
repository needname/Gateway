package Gateway;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Gateway{

    static String fileName1 = "data1.txt";
    static String fileName2 = "data2.txt";
    static int id = -1;

    static long time = 5;
    static InetAddress IP;
    static boolean connect = false;
/*
    public static void main(String args[]) throws Exception {

        PrintWriter writer1 = new PrintWriter(Gateway.fileName1);
        writer1.print("");
        writer1.close();

        PrintWriter writer2 = new PrintWriter(Gateway.fileName2);
        writer2.print("");
        writer2.close();

        InetAddress IP = InetAddress.getLocalHost();
        //new Thread(new TCPGetID(IP,10000)).start();

        String fileName = Gateway.fileName1;


        new Thread(new UDPListening(8080)).start();

        while(true){
            TimeUnit.SECONDS.sleep(Gateway.time);
            //System.out.println(fileName);
            new Thread(new UDPSendData(IP,2000,fileName)).start();
            if(fileName == Gateway.fileName1){
                fileName = Gateway.fileName2;

            }
            else{
                fileName = Gateway.fileName1;
            }
        }

    }
    */
}
