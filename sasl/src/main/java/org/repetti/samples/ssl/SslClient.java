package org.repetti.samples.ssl;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Date: 22/05/15
 */
public class SslClient {
    public static void main(String[] args) {
        Random random = new Random();
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", SslCommon.PORT);
            SslCommon.describeSocket(socket);
            socket.startHandshake();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for (int i = 0; i < 10; i++) {
                int b = reader.read();
                System.out.println(b);
                b = random.nextInt(255);
                System.out.println("> " + b);
                writer.write(b);
            }
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
