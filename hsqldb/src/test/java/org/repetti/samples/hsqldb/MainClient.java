package org.repetti.samples.hsqldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 19/05/15.
 */
public class MainClient {
    private static final Logger log = LoggerFactory.getLogger(MainClient.class);


    public static void main(String[] args) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost:" + MainServer.PORT + "/mydb1", "user", "test");
            // https://en.wikipedia.org/wiki/SQL
            con.createStatement()
                    .executeUpdate(
                            "create table colors (red INTEGER,green INTEGER,blue INTEGER,name varchar(15))");
            con.createStatement()
                    .executeUpdate(
                            "insert into colors values " +
                                    "(255, 0, 0, 'red')" +
                                    ",(0, 255, 0, 'green')" +
                                    ",(0, 0, 255, 'blue')" +
                                    ",(255, 255, 0, 'yellow')" +
                                    ",(0, 255, 255, 'red')" +
                                    ",(255, 0, 255, 'magenta')" +
                                    ",(68, 0, 84, 'deep purple')" +
                                    "");
            ResultSet rs = con.createStatement()
                    .executeQuery(
                            "select * from colors");
            while (rs.next()) {

                int r = rs.getInt(1);
                int g = rs.getInt(2);
                int b = rs.getInt(3);
                String name = rs.getString(4);
                log.info("{}: [{},{},{}]", name, r, g, b);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
