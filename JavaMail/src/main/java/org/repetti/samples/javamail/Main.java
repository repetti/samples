package org.repetti.samples.javamail;

import com.sun.mail.imap.IMAPStore;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Date: 12/05/15
 */
public class Main {
    public static void main(String[] args) throws MessagingException, IOException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("mail.properties"));
        Properties personal = new Properties();
        personal.load(Main.class.getClassLoader().getResourceAsStream("personal.properties"));

        final String user = personal.getProperty("user");
        final String pass = personal.getProperty("pass");
        final String host = personal.getProperty("host");

        Session session = Session.getInstance(props);
        Store store = session.getStore();
        store.connect(host, user, pass);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message message = inbox.getMessage(inbox.getMessageCount());
        Multipart mp = (Multipart) message.getContent();
        BodyPart bp = mp.getBodyPart(0);

        System.out.println(bp.getContent());
    }

    public static void sendMsg() throws IOException {

        String to = "";
        String from = "";
        String host = "smtp.mail.org";

        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("mail.properties"));

        Session session = Session.getInstance(props);

        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test email");
            msg.setSentDate(new Date());

            msg.setText("Line1\nLine 2.");

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void imapSession() throws IOException {

        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("mail.properties"));
        Properties personal = new Properties();
        personal.load(Main.class.getClassLoader().getResourceAsStream("personal.properties"));

        Session session = Session.getInstance(props);
        URLName urlName = new URLName("imap", personal.getProperty("host"), 993, null, personal.getProperty("user"), personal.getProperty("pass"));

        Store s = new IMAPStore(session, urlName);
    }
}
