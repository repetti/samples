package org.repetti.samples.javamail;

import javax.mail.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Date: 12/05/15
 */
public class Simpler {

    static String user;
    static String pass;
    static String host;


    private static void loadData() throws IOException {
        Properties personal = new Properties();
        personal.load(Main.class.getClassLoader().getResourceAsStream("personal.properties"));
        user = personal.getProperty("user");
        pass = personal.getProperty("pass");
        host = personal.getProperty("host");
    }

    public static void main(String[] args) throws MessagingException, IOException {
        loadData();

        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("mail.properties"));
        Session session = Session.getInstance(props);

        Store store = session.getStore();
        store.connect(host, user, pass);
//        Folder inbox = store.getFolder("INBOX");
        Folder[] folders = store.getPersonalNamespaces();
        System.out.println("# Folders count: " + folders.length);
        for (Folder f : folders) {
            getFolderInfo(f);
        }
        store.close();
        System.out.println("# done");
    }

    private static void getFolderInfo(Folder f) throws MessagingException {
        System.out.println("# name: " + f.getName());
        System.out.println("# exists: " + f.exists());
//        System.out.println("# mode: " + f.getMode()); //open
        System.out.println("# permanent flags: " + f.getPermanentFlags());
//        System.out.println("# permanent flags: " + f..getPermanentFlags());
        final int type = f.getType();
        System.out.println("# type: " + type);
        System.out.println("# flags: " + f.getPermanentFlags());
        if ((type & Folder.HOLDS_MESSAGES) != 0) {
            try {
                f.open(Folder.READ_ONLY);
                int count = f.getMessageCount();
                System.out.println("# msg count: " + count);
                if (count > 0) {
                    Message m = f.getMessage(1);
                    System.out.println("### subject: " + m.getSize());
                    System.out.println("### subject: " + m.getSubject());
                    System.out.println("### received: " + m.getReceivedDate());
                    System.out.println("### sent: " + m.getSentDate());
                    System.out.println("### getAllRecipients: " + Arrays.toString(m.getAllRecipients()));
                    System.out.println("### lines: " + m.getLineCount());
                    System.out.println("### getContentType: " + m.getContentType());
                    Multipart mp = (Multipart) m.getContent();

                    BodyPart bp = mp.getBodyPart(0);
                    System.out.println("$$$ ctype: " + bp.getContentType());
//                    System.out.println("$$$ ctype: " + bp.get.getContentType());
                    System.out.println("$$$ " + bp.getContent());
                }
                f.close(false);
            } catch (MessagingException e) {
                System.err.println("# " + e.getMessage());
                System.err.println("# " + e.getNextException());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ((type & Folder.HOLDS_FOLDERS) != 0) {
            for (Folder c : f.list()) {
                getFolderInfo(c);
            }
        }
//        System.out.println(f.getFullName() + ": " + f.getMessageCount() + "-" + f.getUnreadMessageCount());

    }
}