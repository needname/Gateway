/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server;/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * @author DuongThaiMinh
 */
public class Main {

    private static final String CONFIG_PATH = Global.RESOURCE_DIR + Global.SEPARATOR + "config/server-config.properties";
    private static final String HOST;
    private static final int PORT;
    private static final Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger("hcmut.co3003.server.ServerLog");
    private static int executorType = 1;

    static {
        new Global();
        String host;
        int port;
        System.out.println("Welcome to Temperature hcmut.co3003.server.Main");

        try {
            InputStream is = new FileInputStream(new File(CONFIG_PATH).getAbsoluteFile());
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


    public static void main(String[] args) {
        if (PORT != -1) {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                logger.info(String.format("Tempature hcmut.co3003.server.Main has been opened at port %s", PORT));

                while (true) {
                    Socket client = serverSocket.accept();
                    logger.info(String.format("Receive connection request of client: %s", client));
                    Proxy proxy = new Proxy(client);
//                    new Thread(proxy).start();
                    switch (executorType) {
                        case 0:
                            Global.executorSingleThread.submit((Callable<Object>) proxy);
                            break;
                        case 1:
                            Global.executorThreadPool.submit((Callable<Object>) proxy);
                            break;
                        case 2:
                            Global.executorScheThreadPool.submit((Callable<Object>) proxy);
                            break;
                    }
                }
            } catch (IOException e) {
                logger.warning("setting up server failed!");
                logger.warning(e.getMessage());
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }
}
