package org.shine.common.dbutil.proxool;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProxoolExample3 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            PropertyConfigurator
                    .configure("src/main/resources/proxool/proxool-config.properties");
            Connection connection = DriverManager
                    .getConnection("proxool.property-test");
            System.out.println(connection.isClosed());

        } catch (ProxoolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
