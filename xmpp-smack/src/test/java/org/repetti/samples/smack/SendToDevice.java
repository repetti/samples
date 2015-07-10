package org.repetti.samples.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.JidWithLocalpart;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Date: 03/07/15
 */
public class SendToDevice {
    private static final Logger log = LoggerFactory.getLogger(SendToDevice.class);

    static {
    }

    private final String username;
    private final String password;
    private final String server;
    private final String friend;

    public SendToDevice() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader("xmpp-smack/src/test/resources/test.secret"));

        username = prop.getProperty("user");
        password = prop.getProperty("pass");
        server = prop.getProperty("server");
        friend = prop.getProperty("friend");

        if (username == null || password == null || server == null || friend == null) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws InterruptedException, XMPPException, SmackException, IOException {
        new SendToDevice().a2();
    }

    public void a2() throws InterruptedException, XMPPException, SmackException, IOException {
        JidWithLocalpart user = JidCreate.fullFrom(friend);
        XMPPTCPConnectionConfiguration.Builder configBuilder =
                XMPPTCPConnectionConfiguration
                        .builder()
                        .setUsernameAndPassword(username, password)
                        .setServiceName(JidCreate.domainBareFrom(server))
                        .setResource("res");

        System.out.println("connecting");
        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        System.out.println("connected");
        try {
            connection.connect().login();
            System.out.println("logged in");
            Util.getConnectionInfo(connection);

            Roster roster = Roster.getInstanceFor(connection);
            Set<RosterEntry> rosterEntries = roster.getEntries();
            for (RosterEntry re : rosterEntries) {
                System.out.println("@@ " + re);
            }

//            Presence presence = new Presence(Presence.Type.available);
//            presence.setStatus("Gone running");
//            connection.sendStanza(presence);

            ChatManager chatManager = ChatManager.getInstanceFor(connection);

            Chat newChat = chatManager.createChat(user);
            newChat.sendMessage("khgjhgjhgjhg");

            TimeUnit.SECONDS.sleep(10);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

}
