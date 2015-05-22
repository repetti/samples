package org.repetti.samples.avro;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
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
public class MiniTests {

    public static void main(String[] args) throws IOException {
        final Protocol protocol = new Protocol("name", "space");
        protocol.createMessage("mes name", "doc", null, Schema.create(Schema.Type.STRING));

        NettyServer server = new NettyServer(new SpecificResponder(Simple.class, new Simple() {
            @Override
            public Utf8 send(Utf8 msg) throws AvroRemoteException {
                return new Utf8("echo: " + msg.toString());
            }
        }), new InetSocketAddress(Constants.PORT));
        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(Constants.PORT));
        Simple proxy = SpecificRequestor.getClient(Simple.class, client);
        String response = proxy.send(new Utf8("request")).toString();
        System.out.println("Response: " + response);
        client.close();
        server.close();
    }
}
