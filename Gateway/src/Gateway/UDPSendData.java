package Gateway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UDPSendData implements Runnable {

    private final static int PACKETSIZE = 100;
    InetAddress host;
    int port;
    FileReader fr = null;
    BufferedReader br = null;

    UDPSendData(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

        DatagramSocket sendSocket = null;
        try{
            sendSocket = new DatagramSocket();
            fr = new FileReader(Gateway.fileName);
            br = new BufferedReader(fr);
            String sCurrentLine;
            String buffer = "";
            while((sCurrentLine = br.readLine()) != null){
                buffer.concat(sCurrentLine+'\n');
                System.out.println(sCurrentLine);
            }
            byte[] data = buffer.getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
            //sendSocket.send(packet);
            if(br != null)
                br.close();
            if(fr != null)
                fr.close();
        }
        catch(Exception e){
            System.out.println(".");
            System.out.println(e);
        }
        finally {
            if(sendSocket != null){
                sendSocket.close();
            }
        }
    }
}
