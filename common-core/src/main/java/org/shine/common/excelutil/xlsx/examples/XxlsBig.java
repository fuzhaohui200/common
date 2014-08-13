package org.shine.common.excelutil.xlsx.examples;

import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.shine.common.excelutil.xlsx.XxlsAbstract;

import java.sql.*;
import java.util.List;

public class XxlsBig extends XxlsAbstract {
    private Connection conn = null;
    private Statement statement = null;
    private PreparedStatement newStatement = null;
    private String tableName = "users";
    private boolean create = true;

    public XxlsBig(String tableName) throws SQLException {
        this.conn = getNew_Conn();
        this.statement = conn.createStatement();
        this.tableName = tableName;
    }

    public static void main(String[] args) throws Exception {
        XxlsBig howto = new XxlsBig("users");
        howto.processOneSheet("D:/users.xlsx", 1);
        howto.process("D:/users.xlsx");
        howto.close();
    }

    private static Connection getNew_Conn() {
        Connection conn = null;

        try {
            PropertyConfigurator
                    .configure("src/main/resources/proxool/proxool-config.properties");
            conn = DriverManager.getConnection("proxool.property-test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void optRows(int sheetIndex, int curRow, List<String> rowlist)
            throws SQLException {
        if (sheetIndex == 0 && curRow == 0) {
            StringBuffer preSql = new StringBuffer("insert into " + tableName
                    + " values(");
            StringBuffer table = new StringBuffer("create table " + tableName
                    + "(");
            int c = rowlist.size();
            for (int i = 0; i < c; i++) {
                preSql.append("?,");
                table.append(rowlist.get(i));
                table.append(" varchar(100) ,");
            }

            table.deleteCharAt(table.length() - 1);
            preSql.deleteCharAt(preSql.length() - 1);
            table.append(")");
            preSql.append(")");
            if (create) {
                statement = conn.createStatement();
                try {
                    statement.execute("drop table " + tableName);
                } catch (Exception e) {

                } finally {
                    System.out.println("表 " + tableName + " 删除成功");
                }
                if (!statement.execute(table.toString())) {
                    System.out.println("创建表 " + tableName + " 成功");
                    // return;
                } else {
                    System.out.println("创建表 " + tableName + " 失败");
                    return;
                }
            }
            conn.setAutoCommit(false);
            newStatement = conn.prepareStatement(preSql.toString());

        } else if (curRow > 0) {
            // 一般行
            int col = rowlist.size();
            for (int i = 0; i < col; i++) {
                newStatement.setString(i + 1, rowlist.get(i).toString());
            }
            newStatement.addBatch();
            if (curRow % 1000 == 0) {
                newStatement.executeBatch();
                conn.commit();
            }
        }
    }

    public int close() {
        try {
            newStatement.executeBatch();
            conn.commit();
            System.out.println("数据写入完毕");
            this.newStatement.close();
            this.statement.close();
            this.conn.close();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }
}
