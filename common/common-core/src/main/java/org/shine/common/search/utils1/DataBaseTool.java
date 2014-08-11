package org.shine.common.search.utils1;

import java.sql.*;

public class DataBaseTool {

    private String strDriver;
    private String coonnstring;
    private Connection conn;
    private Statement state;

    public DataBaseTool(String strconnect) {
        strDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        coonnstring = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName="
                + strconnect;
        String user = "sa";
        String password = "sa";
        try {
            Class.forName(strDriver).newInstance();//
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(coonnstring, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            state = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DataBaseTool tool = new DataBaseTool("WordList");
        tool.closeDBManager();
    }

    public ResultSet search(String sql) {
        ResultSet rs = null;
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet searchByColumn(String table, String name, String value) {
        ResultSet rs = null;
        String sql = "select * from " + table + " where (" + name + " like '%" + value + "%')";
        System.out.println(sql);
        try {
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean insert(String sql) {
        try {
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(String sql) {
        try {
            state.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closeDBManager() {
        try {
            state.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
