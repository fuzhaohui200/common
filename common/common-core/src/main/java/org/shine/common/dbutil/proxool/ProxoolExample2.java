package org.shine.common.dbutil.proxool;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProxoolExample2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            JAXPConfigurator.configure(
                    "src/main/resources/proxool/proxool-config.xml", false);
            Connection connection = DriverManager
                    .getConnection("proxool.xml-test");
            System.out.println(connection.getAutoCommit());

        } catch (ProxoolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
