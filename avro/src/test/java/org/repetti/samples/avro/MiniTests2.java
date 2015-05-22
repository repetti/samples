package org.repetti.samples.avro;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.ipc.SaslSocketServer;
import org.apache.avro.ipc.SaslSocketTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.util.Utf8;
import org.repetti.samples.avro.generated.Simple;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Date: 22/05/15
 *
 * @author repetti
 */
public class MiniTests2 {

    public static void main(String[] args) throws IOException {
        final Protocol protocol = new Protocol("name", "space");
        protocol.createMessage("mes name", "doc", null, Schema.create(Schema.Type.STRING));
        SaslSocketServer server = new SaslSocketServer(new SpecificResponder(Simple.class, new Simple() {
            @Override
            public Utf8 send(Utf8 msg) throws AvroRemoteException {
                return new Utf8("echo: " + msg.toString());
            }
        }), new InetSocketAddress(Constants.PORT_SECURE));
        SaslSocketTransceiver client = new SaslSocketTransceiver(new InetSocketAddress(Constants.PORT_SECURE));
        Simple proxy = SpecificRequestor.getClient(Simple.class, client);
        String response = proxy.send(new Utf8("request")).toString();
        System.out.println("Response: " + response);
        client.close();
        server.interrupt();
    }
}
