package org.repetti.samples.avro;

import org.apache.avro.util.Utf8;
import org.repetti.samples.avro.generated.Result;
import org.repetti.samples.avro.generated.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Combines both server and client.
 * <p/>
 * See: https://avro.apache.org/docs/current/spec.html
 * <p/>
 * Date: 13/05/15
 */
public class MainAvro {
    private static final Logger log = LoggerFactory.getLogger(MainAvro.class);

    public static void main(String... args) throws IOException {
        final String text = "my comment";

        // Start server first, then client
        MainServer server = new MainServer();
        MainClient client = new MainClient();

        log.info("Everything ready");

        // creating new message to be sent
        Task task = new Task();
        task.setFirst(12);
        task.setSecond(1024);
        task.setComment(new Utf8(text));
        log.info("Task ready: {}", task.toString());

        // sending a mesage and getting a result
        Result result = client.getProxy().send(task);

        log.info("Server returned: {}", result);
        log.info("Cleanup");

        //stopping client and server
        client.close();
        server.stop();

        log.info("Done");

    }
}
