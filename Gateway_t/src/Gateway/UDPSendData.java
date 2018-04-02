package Gateway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSendData implements Runnable {

    private final static int PACKETSIZE = 100;
    InetAddress host;
    int port;
    FileReader fr = null;
    BufferedReader br = null;
    String fileName = null;
    UDPSendData(InetAddress host, int port, String fileName){
        this.host = host;
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        DatagramSocket sendSocket = null;
        try{
            sendSocket = new DatagramSocket();
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String sCurrentLine = "";
            String dataToServer = "";
            while((sCurrentLine = br.readLine()) != null){

                dataToServer = "SET " + Gateway.id + " " + sCurrentLine + fileName;
                byte[] data = dataToServer.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
                sendSocket.send(packet);
                //System.out.println(dataToServer);
            }

            //sendSocket.send(packet);
            if(br != null)
                br.close();
            if(fr != null)
                fr.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            if(sendSocket != null){
                sendSocket.close();
            }
        }
    }
}
