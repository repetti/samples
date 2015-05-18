package org.repetti.samples.thrift;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main method starts MainServer first and then MainClient. After the work is done server is stopped.
 * <p/>
 * Date: 18/05/15
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MainServer.main(null);
            }
        });
        TimeUnit.SECONDS.sleep(1);
        MainClient.main(null);
        executorService.shutdownNow();
        System.exit(0);
    }
}
