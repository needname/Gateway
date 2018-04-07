package Gateway;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import hcmut.co3003.common.lib.dal.FullInformationRecord;

public class UDPListening implements Runnable
{
    private final static int PACKETSIZE = 100;

    private int port;

    UDPListening(int port){
        this.port = port;
    }
    public void run(){
        DatagramSocket udpSocket = null;
        int day, month, year;
        int hour, minute, second;
        float humidity, temperature;
        String id;
        String fileName = Gateway.fileName1;
        long totalTime = 0;
        int i = 0;
        long startTime = 0;
        String data = "";
        StringTokenizer itr = null;
        String temp  = "";
        StringTokenizer itr2 = null;
        Calendar myDate = null;
        Date date = null;
        FullInformationRecord record = null;
        String rw = "";
        byte[] strToBytes;
        long endTime = 0;
        FileOutputStream outputStream = null;

        try{
            udpSocket = new DatagramSocket(port);
            System.out.println("...");
            outputStream = new FileOutputStream(fileName);
            while(true)
            {
                startTime = System.nanoTime();
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                udpSocket.receive(packet);
                data = new String(packet.getData());

                itr = new StringTokenizer(data);

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
                    humidity = Float.parseFloat(itr.nextToken());

                    if((temperature == -1 && humidity == -1) || year != 2018){}
                    else {
                        temperature += 273;
                        humidity /= 100;
                        myDate = new GregorianCalendar(year, month - 1, day, hour, minute, second);
                        date = myDate.getTime();
                        record = new FullInformationRecord(id, temperature, humidity, date);
                        rw = record.toRecord() + "\n";
                        //System.out.print(rw);
                        strToBytes = rw.getBytes();
                        outputStream.write(strToBytes);
                        outputStream.flush();
                    }
                }

                endTime = System.nanoTime();

                totalTime = totalTime + endTime - startTime;

                if(totalTime > 1000000000){
                    //outputStream.close();
                    i = i + 1;
                    totalTime = 0;
                    if(i == Gateway.time){
                        i = 0;
                        if( fileName == Gateway.fileName1){
                            fileName = Gateway.fileName2;

                        }
                        else{
                            fileName = Gateway.fileName1;
                        }
                        outputStream.close();
                        outputStream = new FileOutputStream(fileName);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
            if(udpSocket != null){
                udpSocket.close();
            }
        }
    }
}
