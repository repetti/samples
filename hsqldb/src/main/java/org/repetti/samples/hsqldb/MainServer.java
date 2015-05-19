package org.repetti.samples.hsqldb;

import org.hsqldb.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

/**
 * See http://www.programmingforfuture.com/2011/02/hsqldb-handling-database.html
 * See http://www.hsqldb.org/doc/1.8/guide/guide.html
 * <p/>
 * Created on 19/05/15.
 */
public class MainServer {
    public static final int PORT = 6543;
    private static final Logger log = LoggerFactory.getLogger(MainServer.class);


    public static void main(String[] args) {
//        Server.main(null);
        Server server = new Server();
        server.setAddress("localhost");
        server.setDatabaseName(0, "mydb1");
        //can be file: mem: res:
        // credentials are only needed on DB creation
        server.setDatabasePath(0, "mem:mydb1;user=user;password=test");
        server.setPort(PORT);

        // trace will be printed out
        server.setTrace(true);
        // can be set to null
        server.setLogWriter(new PrintWriter(System.out));

        // to start in new thread (default = false)
        server.setDaemon(false);


        server.start();
    }


}
