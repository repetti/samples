package org.repetti.samples.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.JidWithLocalpart;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Date: 03/07/15
 */
public class MainTest {
    private static final Logger log = LoggerFactory.getLogger(MainTest.class);

    static {
    }

    private final String username;
    private final String password;
    private final String server;
    private final String friend;

    public MainTest() throws IOException {
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
        new MainTest().a2();
    }

    public void a2() throws InterruptedException, XMPPException, SmackException, IOException {
        JidWithLocalpart user = JidCreate.fullFrom(friend);
        XMPPTCPConnectionConfiguration.Builder configBuilder =
                XMPPTCPConnectionConfiguration
                        .builder()
                        .setUsernameAndPassword(username, password)
//                        .setHost(server)
                        .setServiceName(JidCreate.domainBareFrom(server))
                        .setResource("res")
                        .setCompressionEnabled(true);

        System.out.println("connecting");
        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        System.out.println("connected");

        try {
            connection.connect().login();
            System.out.println("logged in");

            getConnectionInfo(connection);

            Roster roster = Roster.getInstanceFor(connection);
            Set<RosterEntry> rosterEntries = roster.getEntries();
            for (RosterEntry re : rosterEntries) {
                System.out.println("@@ " + re);
            }

            roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
//            roster.createEntry(user, "oci", null);

            boolean result = roster.addRosterListener(new RosterListener() {
                @Override
                public void entriesAdded(Collection<Jid> addresses) {

                }

                @Override
                public void entriesUpdated(Collection<Jid> addresses) {

                }

                @Override
                public void entriesDeleted(Collection<Jid> addresses) {

                }

                @Override
                public void presenceChanged(Presence presence) {
                    log.info("presence changed: from {} to {}", presence.getFrom(), presence);
                }
            });

            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Gone running");
            connection.sendStanza(presence);

            ChatManager chatmanager = ChatManager.getInstanceFor(connection);
            chatmanager.addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally) {
                    log.info("chat created: {} {}", chat.getThreadID(), chat);
                    JidWithLocalpart p = chat.getParticipant();
                    log.info("participant: p={} bare={} local={}", p, p.asBareJid(), p.getLocalpart());
                    chat.addMessageListener(
                            new ChatMessageListener() {
                                public void processMessage(Chat chat, Message message) {
                                    System.out.println(chat.getThreadID() + "Received message: " + message);
                                    try {
                                        chat.sendMessage("you wrote: " + message.getBody());
                                    } catch (SmackException.NotConnectedException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            });

            try {
                Chat newChat = chatmanager.createChat(user);
                newChat.sendMessage("http://test.im/\nhttp://www.google.com/page/s?what=dunno");
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }

            TimeUnit.SECONDS.sleep(120);
        } finally {
            connection.disconnect();
        }
    }

    private static String getConnectionInfo(XMPPConnection connection) {
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

    public void a1() throws InterruptedException, XMPPException, SmackException, IOException {
        AbstractXMPPConnection connection = new XMPPTCPConnection(username, password, server);
        connection.connect().login();

        getConnectionInfo(connection);

        TimeUnit.SECONDS.sleep(5);

        connection.disconnect();
    }
}
