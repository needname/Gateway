/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author DuongThaiMinh
 */
public class Global {

    public static final Logger LoggerDefault = Logger.getLogger("hcmut.co3003.server.ServerLog");
    public static final String SEPARATOR = "/";
    public static final String RESOURCE_DIR = ".";
    //    public static final String RESOURCE_DIR = "G:/Projects/CO3003/assignment1/src/main/resources";
    private static final String LOG_DIR = "log";
    private static final String LOG_EXTENSION = ".log";
    private static final int NUMBER_OF_CORE = 4;
    private static final int NUMBER_OF_POOL = 10;
    private static final String SERVER_START_TIME;
    public static ExecutorService executorSingleThread = Executors.newSingleThreadExecutor();
    public static ExecutorService executorThreadPool = Executors.newFixedThreadPool(NUMBER_OF_POOL);
    public static ExecutorService executorScheThreadPool = Executors.newScheduledThreadPool(NUMBER_OF_POOL);


    static {
        System.out.println("Loading global settings");
        SERVER_START_TIME = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        try {
            File logDir = new File(LOG_DIR);
            if (logDir.mkdirs()) {
                System.out.println("Created log folder at:" + logDir.getAbsolutePath());
            }
//            String logFileName = "MyBaseLog_" + SERVER_START_TIME;
            String logFileName = "TestLog";
            String logPath = logDir + SEPARATOR + logFileName + LOG_EXTENSION;
            System.out.println("This session log file: " + logFileName);
            FileHandler handler = new FileHandler(logPath);
            handler.setFormatter(new SimpleFormatter());
            LoggerDefault.addHandler(handler);
//            LoggerDefault.setUseParentHandlers(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
