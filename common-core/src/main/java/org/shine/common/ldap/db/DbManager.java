package org.shine.common.ldap.db;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class DbManager {
    private Connection conn = null;
    private int inUsed = 0;
    @SuppressWarnings("rawtypes")
    private List freeConnections = new ArrayList();

    private int maxConn; // 最大连接
    private String name; // 连接池名字
    private String password; // 密码
    private String url; // 数据库连接地址
    private String driver; // 驱动
    private String user; // 用户名

    public DbManager() {
        init();
    }

    private void init() {
        Properties config = new Properties();
        // try {
        // InputStream in =
        // DbManager.class.getResourceAsStream("db.properties");
        // config.load(in);
        // Class.forName(config.getProperty("mysql_driver"));
        // conn = DriverManager.getConnection(config.getProperty("mysql_url"),
        // config.getProperty("mysql_user"),
        // config.getProperty("mysql_password"));
        // conn.setAutoCommit(true);
        // in.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        try {
            InputStream in = DbManager.class
                    .getResourceAsStream("db.properties");
            config.load(in);
            Class.forName(config.getProperty("local_driver"));
            conn = DriverManager.getConnection(config.getProperty("local_url"),
                    config.getProperty("local_user"),
                    config.getProperty("local_password"));
            conn.setAutoCommit(true);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() {
        Connection con = null;
        if (this.freeConnections.size() > 0) {
            con = (Connection) this.freeConnections.get(0);
            this.freeConnections.remove(0);// 如果连接分配出去了，就从空闲连接里删除
            if (con == null)
                con = getConnection(); // 继续获得连接
        } else {
            con = newConnection(); // 新建连接
        }
        if (this.maxConn == 0 || this.maxConn < this.inUsed) {
            con = null;// 等待 超过最大连接时
        }
        if (con != null) {
            this.inUsed++;
            System.out.println("得到　" + this.name + "　的连接，现有" + inUsed
                    + "个连接在使用!");
        }
        return con;
    }

    public synchronized void release() {
        Iterator<?> allConns = this.freeConnections.iterator();
        while (allConns.hasNext()) {
            Connection con = (Connection) allConns.next();
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        this.freeConnections.clear();

    }

    private Connection newConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("sorry can't find db driver!");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("sorry can't create Connection!");
        }
        return conn;

    }

    // public Connection getConnection(){
    // return this.conn;
    // }

    public void closeConnection() {
        if (conn == null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stmt;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pstmt;
    }

    public void closeConnection(Connection con, boolean abortTransaction) {
        if (con == null) {
            return;
        } else {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {

        }
    }

    public void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {

        }
    }

    public void closeConnection(Statement stmt, Connection con) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {

        }
        closeConnection(con);
    }

    public void closeConnection(PreparedStatement pstmt, Connection con) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {

        }
        closeConnection(con);
    }

    public void closePreparedStatement(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (Exception e) {

        }
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {

        }
    }

    public void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(con);
    }

}
