/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleCallable {

    public static void main(String[] args) throws Exception {
        int num = 5;
        String ids[] = {"sensor-01", "sensor-02", "sensor-03", "sensor-04", "sensor-05"};
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        while (num-- > 0) {
            String id = ids[num];
            System.out.println("Get data with id = " + id);
            Connection connection = new Connection(id);
            Future<String> result = executorService.submit(connection);

            new Thread(new ConnetionNoReturn()).start();
//            connection.call();
        }
    }

    static class Connection implements Callable<String> {
        String id;

        Connection(String id) {
            this.id = id;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public String call() throws Exception {
            String data;
            Thread.sleep(500);
            System.out.println("I'm doing!");
            // do something
            data = "data" + this.id;
            //
            return data;
        }
    }

    static class ConnetionNoReturn implements Runnable{

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello, I'm done!");
        }
    }
}
