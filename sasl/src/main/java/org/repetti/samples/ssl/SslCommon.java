package org.repetti.samples.ssl;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * Date: 22/05/15
 */
public class SslCommon {
    public static final int PORT = 9999;

    public static void describeSocket(SSLSocket socket) {
        StringBuilder sb = new StringBuilder();
        sb.append(socket.getClass()).append(":");
        sb.append("\n Remote address: ").append(socket.getInetAddress());
        sb.append("\n Remote port: ").append(socket.getPort());
        sb.append("\n Local socket address: ").append(socket.getLocalSocketAddress());
        sb.append("\n Local address: ").append(socket.getLocalAddress());
        sb.append("\n Local port: ").append(socket.getLocalPort());
        sb.append("\n Need client auth: ").append(socket.getNeedClientAuth());
        SSLSession session = socket.getSession();
        sb.append("\n Cipher suite: ").append(session.getCipherSuite());
        sb.append("\n Protocol: ").append(session.getProtocol());
        System.out.println(sb.toString());
    }
}
