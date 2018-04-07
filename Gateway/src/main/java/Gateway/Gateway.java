package Gateway;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Gateway{

    public static String fileName1 = "data1.txt";
    public static String fileName2 = "data2.txt";
    public static String id = "no_id";

    public static long time = 5;
    public static InetAddress IP;
    public static int port = 10000;
    public static boolean connect = false;
    public static boolean udpListening = false;
}
