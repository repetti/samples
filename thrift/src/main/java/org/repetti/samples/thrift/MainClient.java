package org.repetti.samples.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.repetti.samples.thrift.generated.BadMonkeyException;
import org.repetti.samples.thrift.generated.ExtendedService;
import org.repetti.samples.thrift.generated.Response;
import org.repetti.samples.thrift.generated.SpaceMonkey;
import org.repetti.samples.thrift.generated.mainConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 18/05/15
 */
public class MainClient {
    private static final Logger log = LoggerFactory.getLogger(MainClient.class);

    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            ExtendedService.Client client = new ExtendedService.Client(protocol);
            doSomething(client);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void doSomething(ExtendedService.Client client) throws TException {
        int res = client.sum(20, mainConstants.MYFAVOURITECONSTANT);
        log.info("{}+{}={}", 20, mainConstants.MYFAVOURITECONSTANT, res);

        log.info("knock knock...");
        client.knockKnock();

        SpaceMonkey[] spaceMonkeys = {
                new SpaceMonkey("Bob", 140, 40, true),
                new SpaceMonkey("Angel face", 70, 31, true),
                new SpaceMonkey("Marla", 60, 30, true)
        };
        for (SpaceMonkey m : spaceMonkeys) {
            try {
                Response r = client.inspect(m);
                log.info("{}, {}", m.name, r.getMsg());
            } catch (BadMonkeyException e) {
                log.error("Error", e);
            }
        }
    }
}
