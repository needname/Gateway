package Gateway;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import hcmut.co3003.common.lib.dal.FullInformationRecord;

public class UDP implements Runnable
{
    private final static int PACKETSIZE = 100;

    private InetAddress server;

    private int listenPort;
    private int serverPort;

    UDP(int listenPort, InetAddress server, int serverPort){
        this.listenPort = listenPort;
        this.server = server;
        this.serverPort = serverPort;
    }
    public void run(){
        try{
            DatagramSocket udpListenSocket;
            udpListenSocket = new DatagramSocket(listenPort);
            System.out.println("...");
            DatagramPacket packetRcv;
            String temp;
            String data;
            StringTokenizer itr2;
            Calendar myDate;
            Date date;
            FullInformationRecord record;

            int day, month, year;
            int hour, minute, second;
            float humidity, temperature;
            String id;

            DatagramSocket udpSendServer;
            udpSendServer = new DatagramSocket();
            String dataToServer = "";
            byte[] strToBytes;
            byte[] dataSend;
            DatagramPacket packetSend;
            String rw;

            //long totalTime = 0;
            //int i = 0;
            while(true)
            {
                //long startTime = System.nanoTime();
                packetRcv = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                udpListenSocket.receive(packetRcv);
                data = new String(packetRcv.getData());

                StringTokenizer itr = new StringTokenizer(data);

                if(itr.hasMoreTokens()){

                    id = itr.nextToken();
                    day = Integer.parseInt(itr.nextToken());
                    month = Integer.parseInt(itr.nextToken());
                    year = Integer.parseInt(itr.nextToken());

                    temp  = itr.nextToken();
                    itr2 = new StringTokenizer(temp,":");
                    hour = Integer.parseInt(itr2.nextToken());
                    minute = Integer.parseInt(itr2.nextToken());
                    second = Integer.parseInt(itr2.nextToken());

                    temperature = Float.parseFloat(itr.nextToken());
                    humidity = Float.parseFloat(itr.nextToken()) ;

                    if((temperature == -1 && humidity == -1) || year != 2018){}
                    else {
                        temperature += 273;
                        humidity /= 1000;
                        myDate = new GregorianCalendar(year, month - 1, day, hour, minute, second);
                        date = myDate.getTime();
                        record = new FullInformationRecord(id, temperature, humidity, date);
                        rw = record.toRecord() + "\n";
                        System.out.print(rw);
                        strToBytes = rw.getBytes();
                        dataToServer = "SET " + Gateway.id + " " + rw;
                        dataSend = dataToServer.getBytes();
                        packetSend = new DatagramPacket(dataSend, dataSend.length, server, serverPort);

                        udpSendServer.send(packetSend);
                        System.out.println(dataToServer);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}


