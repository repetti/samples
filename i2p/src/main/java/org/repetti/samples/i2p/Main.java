package org.repetti.samples.i2p;

import net.i2p.client.I2PSession;
import net.i2p.client.streaming.I2PSocketManager;
import net.i2p.client.streaming.I2PSocketManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://geti2p.net/en/get-involved/develop/applications#start.streaming
 * <p/>
 * Requires:
 * <p/>
 * i2p.jar
 * mstreaming.jar
 * streaming.jar
 * <p/>
 * Date: 15/05/15
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialization
        I2PSocketManager manager = I2PSocketManagerFactory.createManager();
        I2PSession session = manager.getSession();
        //Print the base64 string, the regular string would look like garbage.
        log.info("dest={}", session.getMyDestination().toBase64());
    }
}
