package org.repetti.samples.avro;

import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.repetti.samples.avro.generated.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Class providing a proxy connection to the server, that should implement Calculator interface.
 *
 * Date: 13/05/15
 */
public class MainClient {
    private static final Logger log = LoggerFactory.getLogger(MainClient.class);
    private final Calculator proxy;
    private final NettyTransceiver client;

    /**
     * Client constructor, that creates a proxy accessing the server implementing Calculator interface.
     * @throws IOException if connection cannot be established
     */
    public MainClient() throws IOException {
        log.info("Client stating");
        this.client = new NettyTransceiver(new InetSocketAddress(Constants.PORT));
        this.proxy = SpecificRequestor.getClient(Calculator.class, client);
        log.info("Client started, proxy obtained");
    }

    /**
     * Get method for a proxy object.
     *
     * @return a Calculator proxy instance
     */
    public Calculator getProxy() {
        return proxy;
    }

    /**
     * Closes the connection to the server.
     */
    public void close() {
        this.client.close();
    }
}
