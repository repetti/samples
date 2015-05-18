package org.repetti.samples.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.repetti.samples.thrift.generated.BadMonkeyException;
import org.repetti.samples.thrift.generated.ExtendedService;
import org.repetti.samples.thrift.generated.Response;
import org.repetti.samples.thrift.generated.Result;
import org.repetti.samples.thrift.generated.SpaceMonkey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * See https://thrift.apache.org/tutorial/java
 * <p/>
 * Date: 18/05/15
 */
public class MainServer {
    private static final Logger log = LoggerFactory.getLogger(MainServer.class);

    public static void main(String[] args) {
        try {
            MonkeyService service = new MonkeyService();
            final ExtendedService.Processor processor = new ExtendedService.Processor(service);
            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };
            new Thread(simple).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(ExtendedService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            log.info("Starting the server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MonkeyService implements ExtendedService.Iface {

        @Override
        public int sum(int first, int second) throws TException {
            log.info("sum({},{})", first, second);
            return first + second;
        }

        @Override
        public Response inspect(SpaceMonkey candidate) throws BadMonkeyException, TException {
            log.info("Candidate: {}", candidate);
            if (candidate.getName().equalsIgnoreCase("Marla")) {
                throw new BadMonkeyException("you are not a monkey!");

            }
            if (!candidate.isJustCame()) {
                return new Response("come in.", Result.SUCCESS);
            }
            if (candidate.weight > 90) {
                return new Response("you are too fat!", Result.FAIL);
            }
            if (candidate.weight < 75) {
                return new Response("you are too skinny!", Result.FAIL);
            }
            return new Response("you are too blonde!", Result.FAIL);
        }

        @Override
        public void knockKnock() throws TException {
            log.info("Knock knock!");
        }
    }
}
