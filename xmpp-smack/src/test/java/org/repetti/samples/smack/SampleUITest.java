package org.repetti.samples.smack;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Date: 09/07/15
 *
 * @author repetti
 */
public class SampleUITest {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader("xmpp-smack/src/test/resources/test.secret"));

        String username = prop.getProperty("user");
        String password = prop.getProperty("pass");
        String server = prop.getProperty("server");
        String friend = prop.getProperty("friend");

        if (username == null || password == null || server == null || friend == null) {
            throw new RuntimeException();
        }

        new SimpleUI(server, username, password, friend);
    }
}
