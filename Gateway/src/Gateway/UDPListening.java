package Gateway;

import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class UDPListening implements Runnable
{
    private final static int PACKETSIZE = 100;

    int port;

    UDPListening(int port){
        this.port = port;
    }
    public void run(){
        try{
            DatagramSocket udpSocket;
            udpSocket = new DatagramSocket(port);
            System.out.println("...");

            int day, month, year;
            int hour, minute, second;
            float humidity, temperature;
            String id;

            FileOutputStream outputStream = new FileOutputStream(Gateway.fileName);

            while(true)
            {

                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                udpSocket.receive(packet);
                String data = new String(packet.getData());

                //String data = "sensor1 1 4 2018 9:32:44 -1.0 -1.0";

                StringTokenizer itr = new StringTokenizer(data);

                if(itr.hasMoreTokens()){

                    id = itr.nextToken();
                    day = Integer.parseInt(itr.nextToken());
                    month = Integer.parseInt(itr.nextToken());
                    year = Integer.parseInt(itr.nextToken());

                    String temp  = itr.nextToken();
                    StringTokenizer itr2 = new StringTokenizer(temp,":");
                    hour = Integer.parseInt(itr2.nextToken());
                    minute = Integer.parseInt(itr2.nextToken());
                    second = Integer.parseInt(itr2.nextToken());

                    temperature = Float.parseFloat(itr.nextToken());
                    humidity = Float.parseFloat(itr.nextToken());

                    if((temperature == -1 && humidity == -1) || year != 2018){}
                    else{
                        temperature += 273;
                        //System.out.println(id + " " + day + " " + month + " " + year + " " + hour + " " + minute + " " + second + " " + temperature + " " + humidity);
                        Calendar mydate = new GregorianCalendar(year, month, day, hour,minute,second);
                        Date date = mydate.getTime();
                        String record = id + " " + date.toString() + " " + temperature + " " + humidity + "\n";
                        System.out.print(record);
                        byte[] strToBytes = record.getBytes();
                        outputStream.write(strToBytes);
                        outputStream.flush();
                    }
                }
                //outputStream.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

