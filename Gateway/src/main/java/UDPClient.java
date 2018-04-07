import java.net.*;
import java.util.concurrent.TimeUnit;

public class UDPClient
{
    private final static int PACKETSIZE = 100;
    public static void main(String args[])
    {
        DatagramSocket socket = null;
        try
        {
            InetAddress host = InetAddress.getLocalHost();
            int port = 8080;

            socket = new DatagramSocket();
            int i = 53;
            while(true) {
                i = i + 1;
                String name = "sensor1 1 4 2018 24:30:10 31.0 " + i;
                TimeUnit.SECONDS.sleep(1);
                byte[] data = name.getBytes();

                DatagramPacket packet = new DatagramPacket(data, data.length, host, port);

                socket.send(packet);

                //socket.setSoTimeout(2000);

                //packet.setData(new byte[PACKETSIZE]);
                //socket.receive(packet);

                //System.out.println(new String(packet.getData()));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            if(socket != null)
                socket.close();
        }
    }
}
