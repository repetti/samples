package org.repetti.samples.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.JidWithLocalpart;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Date: 09/07/15
 */
public class SimpleUI extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(SimpleUI.class);

    //    private static final int keyLength = 256;
//    private static final int iterationCount = 100;
//    private static final String algorithm = "AES/CTR/PKCS7Padding";
    private final JButton buttonSend;
    private final JButton buttonConnect;
    private final JButton buttonDisconnect;
    private final JTextField fieldPath;
    private final JScrollPane scroll;
    private final JTextArea text;

    private final String server;
    private final String username;
    private final String password;
    private final String friend;

    private AbstractXMPPConnection connection;
    private Chat newChat;

    public SimpleUI(String server, String username, String password, String friend)
            throws HeadlessException, XmppStringprepException {
        this.server = server;
        this.username = username;
        this.password = password;
        this.friend = friend;

        connect();

        setTitle("XS Simple UI");
        this.setLayout(null);
        buttonSend = new JButton("Send");
        buttonDisconnect = new JButton("-s");
        buttonConnect = new JButton("-l");
        fieldPath = new JTextField();
        text = new JTextArea();
        text.setEditable(true);
        scroll = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    send();
                } catch (SmackException.NotConnectedException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connect();
                } catch (XmppStringprepException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });

        final int w = 640;
        final int h = 480;
        final int b = 5;
        final int bw = 120;
        final int eh = 25;
        final int fw = w - b * 5 - bw * 3;

        fieldPath.setBounds(b, b, fw, eh);
        buttonSend.setBounds(b * 2 + fw, b, bw, eh);
        buttonConnect.setBounds(b * 3 + fw + bw, b, bw, eh);
        buttonDisconnect.setBounds(b * 4 + fw + bw * 2, b, bw, eh);
        scroll.setBounds(b, b * 2 + eh, w - b * 2, h - eh - b * 2);

        Container pane = this.getContentPane();
        pane.add(fieldPath);
        pane.add(buttonConnect);
        pane.add(buttonDisconnect);
        pane.add(buttonSend);
        pane.add(scroll);

        this.pack(); //to get insets of the window
        Insets i = this.getInsets();
        log.debug("{}", i);
        this.setSize(w + i.left + i.right, h + i.top + i.bottom);
        this.setResizable(false);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void connect() throws XmppStringprepException {
        JidWithLocalpart user = JidCreate.fullFrom(friend);
        XMPPTCPConnectionConfiguration.Builder configBuilder =
                XMPPTCPConnectionConfiguration
                        .builder()
                        .setUsernameAndPassword(username, password)
                        .setServiceName(JidCreate.domainBareFrom(server))
                        .setResource("res");

        System.out.println("connecting");
        connection = new XMPPTCPConnection(configBuilder.build());
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

            newChat = chatManager.createChat(user);
//            newChat.sendMessage("http://www.worldweatheronline.com/v2/weather.aspx?q=Saarlouis,%20Germany");

            TimeUnit.SECONDS.sleep(10);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            connection.disconnect();
        }
    }

    private void send() throws SmackException.NotConnectedException, InterruptedException {
        newChat.sendMessage(text.getText());
    }

    public void disconnect() {
        connection.disconnect();
    }

//    @Nullable
//    private String getPassword(String title) {
//        // as described in http://blogger.ziesemer.com/2007/03/java-password-dialog.html
//        JLabel label = new JLabel("Please enter your password:");
//        JPasswordField passwordField = new JPasswordField();
//        int status = JOptionPane.showConfirmDialog(null,
//                new Object[]{label, passwordField}, title,
//                JOptionPane.OK_CANCEL_OPTION);
//
//        if (status == JOptionPane.OK_OPTION) {
//            return String.valueOf(passwordField.getPassword());
//        }
//        return null;
//    }
}
