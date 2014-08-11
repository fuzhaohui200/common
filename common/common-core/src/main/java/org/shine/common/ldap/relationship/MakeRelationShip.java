package org.shine.common.ldap.relationship;

import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.JiveConstants;
import org.shine.common.ldap.utils.common.SequenceManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MakeRelationShip {

    private static final String CREATE_ROSTER_ITEM = "INSERT INTO ofRoster (username, rosterID, jid, sub, ask, recv, nick) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_ROSTER_ITEM_GROUPS = "INSERT INTO ofRosterGroups (rosterID, rank, groupName) VALUES (?, ?, ?)";

    private static DbManager dbutils = null;

    static {
        dbutils = new DbManager();
    }

    public static void createItem(String username, String jid, String nickName) {
        PreparedStatement pstmt = null;
        try {
            long rosterID = SequenceManager.nextID(JiveConstants.ROSTER);
            pstmt = dbutils.getPreparedStatement(CREATE_ROSTER_ITEM);
            pstmt.setString(1, username);
            pstmt.setLong(2, rosterID);
            pstmt.setString(3, jid);
            pstmt.setInt(4, 3);
            pstmt.setInt(5, -1);
            pstmt.setInt(6, -1);
            pstmt.setString(7, nickName);
            pstmt.executeUpdate();
            insertGroups(rosterID, "我的同学");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbutils.closePreparedStatement(pstmt);
            dbutils.closeConnection();
        }
    }

    private static void insertGroups(long rosterID, String groupName) {
        PreparedStatement pstmt = null;
        try {
            pstmt = dbutils.getPreparedStatement(CREATE_ROSTER_ITEM_GROUPS);
            pstmt.setLong(1, rosterID);
            pstmt.setInt(2, 0);
            pstmt.setString(3, groupName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbutils.closePreparedStatement(pstmt);
        }
    }

}
