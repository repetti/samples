package org.repetti.samples.smack;

import org.jivesoftware.smack.XMPPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 09/07/15
 *
 * @author repetti
 */
public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static String getConnectionInfo(XMPPConnection connection) {
        log.info("getServiceName {}", connection.getServiceName());
        log.info("Host {}", connection.getHost());
        log.info("getPort {}", connection.getPort());
        log.info("User {}", connection.getUser());
        log.info("getStreamId {}", connection.getStreamId());
        log.info("getFromMode {}", connection.getFromMode());
        log.info("isConnected {}", connection.isConnected());
        log.info("isAuthenticated {}", connection.isAuthenticated());
        log.info("isAnonymous {}", connection.isAnonymous());
        log.info("isSecureConnection {}", connection.isSecureConnection());
        log.info("isUsingCompression {}", connection.isUsingCompression());
        return null;
    }
}
