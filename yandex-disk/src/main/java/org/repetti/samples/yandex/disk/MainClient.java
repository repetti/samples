package org.repetti.samples.yandex.disk;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.ProgressListener;
import com.yandex.disk.rest.ResourcesArgs;
import com.yandex.disk.rest.RestClient;
import com.yandex.disk.rest.exceptions.ServerException;
import com.yandex.disk.rest.json.Resource;
import com.yandex.disk.rest.json.ResourceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Date: 10/06/15
 */
public class MainClient {
    private static final Logger logger = LoggerFactory.getLogger(MainClient.class);

    public static void main(String[] args) throws IOException, ServerException {
//        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        logger.info("pwd: " + new File(".").getAbsolutePath());

        String user = null;
        String token = null;
        try {
            FileInputStream propertiesFile = new FileInputStream("local.properties");
            Properties properties = new Properties();
            properties.load(propertiesFile);
            user = properties.getProperty("test.user");
            token = properties.getProperty("test.token");
        } catch (FileNotFoundException ex) {
            logger.info("local.properties", ex);
        }
        if (user == null || token == null) {
            user = "yadiskrestapitest";
            token = "4dc977dfd8024be1bba27a5fefc1c1d0";
        }

        Credentials credentials = new Credentials(user, token);

        RestClient client = new RestClient(credentials);
//        client.getClient().networkInterceptors().add(new LoggingInterceptor(true));
        logger.info("{}", client.getDiskInfo());
        logger.info("{}", client.getApiVersion());
        ResourceList a = client.getFlatResourceList(new ResourcesArgs.Builder().setLimit(1000000).build());
        for (Resource b : a.getItems()) {

            logger.info("# {}", b);
            client.downloadFile(b.getPath().getPath(), new File("/home/peter/tmp/1e/x" + b.getName()), new ProgressListener() {
                AtomicInteger last = new AtomicInteger(-1);

                @Override
                public void updateProgress(long loaded, long total) {
                    double p = total <= 0 ? -1 : (float) loaded / total;
                    if (p > last.get() + 5) {
                        last.set((int) p);
                        System.out.print(p + "%..");
                    }
                }

                @Override
                public boolean hasCancelled() {
                    return false;
                }
            });
        }

        logger.info("done");
    }
}
