package Gateway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSendData implements Runnable {

    private final static int PACKETSIZE = 100;
    private InetAddress host;
    private int port;
    private FileReader fr = null;
    private BufferedReader br = null;
    private String fileName = null;
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

                dataToServer = "SET " + Gateway.id + " " + sCurrentLine;
                byte[] data = dataToServer.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
                System.out.println(host.toString());
                System.out.println(port);

                sendSocket.send(packet);
                sendSocket.setSoTimeout(5000);
                System.out.println(dataToServer);
                packet.setData(new byte[PACKETSIZE]);
                sendSocket.receive(packet);
                //System.out.println(new String(packet.getData()));

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
