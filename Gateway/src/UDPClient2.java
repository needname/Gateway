import java.net.*;

public class UDPClient2
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
            while(true) {
                byte[] data = "sensor1 Sun Apr  1 00:46:00 2018 30.1 80.0".getBytes();

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
