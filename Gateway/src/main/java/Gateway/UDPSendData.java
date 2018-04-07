package Gateway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSendData implements Runnable {

    private final static int PACKETSIZE = 100;
    private InetAddress host;
    private int port;
    private FileReader fr = null;
    private BufferedReader br = null;
    private String fileName = "";
    UDPSendData(InetAddress host, int port, String fileName){
        this.host = host;
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        DatagramSocket sendSocket = null;
        DatagramPacket packet;
        String sCurrentLine = "";
        String dataToServer = "";
        byte[] data;
        try{
            sendSocket = new DatagramSocket();
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            while((sCurrentLine = br.readLine()) != null){
                dataToServer = "SET " + Gateway.id + " " + sCurrentLine;
                data = dataToServer.getBytes();
                packet = new DatagramPacket(data, data.length, host, port);

                sendSocket.send(packet);
                //System.out.println(dataToServer);
            }

            //sendSocket.send(packet);
            if(br != null) {
                br.close();
            }
            if(fr != null) {
                fr.close();
                PrintWriter writer = new PrintWriter(fileName);
                writer.print("");
                writer.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
            if(sendSocket != null){
                sendSocket.close();
            }
        }
        finally {
           if(sendSocket != null){
                sendSocket.close();
            }
        }
    }
}