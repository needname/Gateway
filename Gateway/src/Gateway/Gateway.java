package Gateway;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Gateway{

    static String fileName = "data.txt";
    static int id;

    public static void main(String args[]) throws Exception {

        new Thread(new UDPListening(8080)).start();
        //new Thread(new UDPListening(8080)).start();

        InetAddress IP = InetAddress.getLocalHost();
        //new Thread(new TCPGetID(IP,10000)).start();

        //new Thread(new UDPSendData(IP,1000)).start();
    }
}
