package org.repetti.samples.avro;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.repetti.samples.avro.generated.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Date: 13/05/15
 */
public class MainClient {
    private static final Logger log = LoggerFactory.getLogger(MainClient.class);
    private final Calculator proxy;
    private final NettyTransceiver client;

    public MainClient() throws IOException {
        log.info("Client stating");
        this.client = new NettyTransceiver(new InetSocketAddress(9999));
        this.proxy = SpecificRequestor.getClient(Calculator.class, client);
        log.info("Client started, proxy obtained");
    }

    public Calculator getProxy() {
        return proxy;
    }

    public void close() {
        this.client.close();
    }
}
