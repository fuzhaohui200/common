package org.shine.common.ldap.exchange;

import org.shine.common.ldap.db.DbManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class MessagePush implements IMessagePush {

    public static void main(String args[]) {
        String content = "ffdasfasfads";
        String url = "http://www.google.com.hk";
        String jid = "45678912@jibber.com";
        int mark = 1;
        new MessagePush().addMessage(content, url, jid, mark);
    }

    @SuppressWarnings("deprecation")
    public void addMessage(String content, String url, String jid, int mark) {
        DbManager dbutils = new DbManager();
        PreparedStatement pstmt = null;
        String sql = "insert into notice(content, url, jid, reachTime, mark) values(?, ?, ?, ?, ?)";
        try {
            String time = new Date().toLocaleString();
            String reachTime = time.substring(0, time.lastIndexOf(":"));
            pstmt = dbutils.getPreparedStatement(sql);
            pstmt.setString(1, content);
            pstmt.setString(2, url);
            pstmt.setString(3, jid);
            pstmt.setString(4, reachTime);
            pstmt.setInt(5, mark);
            pstmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbutils.closePreparedStatement(pstmt);
        dbutils.closeConnection();

    }

}
