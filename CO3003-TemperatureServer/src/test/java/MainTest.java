/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import hcmut.co3003.server.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

class MainTest {

    private static final String CONFIG_PATH = "config/server-config.properties";
    private static final String HOST;
    private static final int PORT;
    private static final Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static int cnt = 0;

    static {
        String host;
        int port;
        System.out.println("Welcome to Temperature hcmut.co3003.server.Main test");

        try {
            InputStream is = new FileInputStream(CONFIG_PATH);
            properties.load(is);
            host = properties.getProperty("server.host");
            port = Integer.parseInt(properties.getProperty("server.port"));
        } catch (IOException e) {
            logger.warning("Can't access config file!\n" + e.getMessage());
            host = null;
            port = -1;
        }

        HOST = host;
        PORT = port;
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.printf("============ This is test case no.%2d ============\n\n", cnt++);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("/>END TEST CASE");
    }

    @Test
    void testConnection() throws Exception {
        Socket socket = new Socket(HOST, PORT);
        socket.close();
    }

    @Test
    void testMultipleConnection() throws Exception {
        int n = 10;
        for (int i = 0; i < n; ++i) {
            Socket socket = new Socket(HOST, PORT);
            socket.close();
        }
    }

    @Test
    void testSendRequest() throws Exception {
        Socket socket = new Socket(HOST, PORT);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        os.write("Hello hcmut.co3003.server.Main".getBytes());
        os.flush();
        socket.close();
    }

    @Test
    void testReceiveRequest() throws Exception {
        Socket socket = new Socket(HOST, PORT);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

//        os.write("Hello hcmut.co3003.server.Main".getBytes());
//        os.flush();

        byte[] expectValue = "Hello client".getBytes();
        byte[] returnValue = new byte[expectValue.length];
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(2), () -> is.read(returnValue));
        Assertions.assertArrayEquals(returnValue, expectValue);
        socket.close();
    }
}