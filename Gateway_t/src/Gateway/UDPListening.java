package Gateway;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import hcmut.co3003.common.lib.dal.FullInformationRecord;

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

            String fileName = Gateway.fileName1;
            FileOutputStream outputStream = new FileOutputStream(fileName);

            long totalTime = 0;
            int i = 0;
            while(true)
            {
                long startTime = System.nanoTime();
                DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                udpSocket.receive(packet);
                String data = new String(packet.getData());

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
                    else {
                        temperature += 273;
                        //System.out.println(id + " " + day + " " + month + " " + year + " " + hour + " " + minute + " " + second + " " + temperature + " " + humidity);
                        Calendar mydate = new GregorianCalendar(year, month - 1, day, hour, minute, second);
                        Date date = mydate.getTime();
                        //String record_new = id + " " + date.toString() + " " + temperature + " " + humidity + "\n";
                        FullInformationRecord record = new FullInformationRecord(id, temperature, humidity, date);
                        //System.out.print(record_new);
                        String rw = record.toRecord() + "\n";
                        System.out.print(rw);
                        byte[] strToBytes = rw.getBytes();
                        System.out.println(fileName);
                        outputStream.write(strToBytes);
                        outputStream.flush();
                    }
                }

                //TimeUnit.SECONDS.sleep(1);
                long endTime = System.nanoTime();

                totalTime = totalTime + endTime - startTime;

                if(totalTime > 1000000000){
                    //System.out.println(totalTime);
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
                //outputStream.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

