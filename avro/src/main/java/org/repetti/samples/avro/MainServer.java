package org.repetti.samples.avro;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;
import org.repetti.samples.avro.generated.Calculator;
import org.repetti.samples.avro.generated.Result;
import org.repetti.samples.avro.generated.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Date: 13/05/15
 */
public class MainServer {
    private static final Logger log = LoggerFactory.getLogger(MainServer.class);
    private final Server server;

    public MainServer() {
        log.info("Starting");
        server = new NettyServer(
                new SpecificResponder(Calculator.class, new CalcImpl()), new InetSocketAddress(9999));
        log.info("Started");
    }

    public void stop() {
        server.close();
    }

    public static class CalcImpl implements Calculator {
        public Result send(Task task) {
            log.info("Got task: {}", task);
            final int a = task.getFirst();
            final int b = task.getSecond();
            final int sum = a + b;
            return Result.newBuilder()
                    .setSum(sum)
                    .setComment(new Utf8(a + "+" + b + "=" + sum + ", your comment was: " + task.getComment()))
                    .build();
        }
    }
}