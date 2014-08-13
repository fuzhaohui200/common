package org.shine.common.dbutil.proxool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProxoolExample1 {

    private static Logger logger = LoggerFactory
            .getLogger(ProxoolExample1.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            try {
                connection = DriverManager
                        .getConnection("proxool.example:org.hsqldb.jdbcDriver:jdbc:hsqldb:test");
            } catch (SQLException e) {
                logger.error("Problem getting connection", e);
            }

            if (connection != null) {
                logger.info("Got connection :)");
            } else {
                logger.error("Didn't get connection, which probably means that no Driver accepted the URL");
            }

        } catch (ClassNotFoundException e) {
            logger.error("Couldn't find driver", e);
        } finally {
            try {
                // Check to see we actually got a connection before we
                // attempt to close it.
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Problem closing connection", e);
            }
        }

    }

}
