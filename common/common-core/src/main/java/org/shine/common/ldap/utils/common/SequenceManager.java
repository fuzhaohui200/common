package org.shine.common.ldap.utils.common;

import org.shine.common.ldap.db.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SequenceManager {

    private static final String CREATE_ID = "INSERT INTO ofID (id, idType) VALUES (1, ?)";
    private static final String LOAD_ID = "SELECT id FROM ofID WHERE idType=?";
    private static final String UPDATE_ID = "UPDATE ofID SET id=? WHERE idType=? AND id=?";
    private static DbManager dbutils = null;
    private static Map<Integer, SequenceManager> managers = new ConcurrentHashMap<Integer, SequenceManager>();
    static {
        new SequenceManager(JiveConstants.ROSTER, 5);
        new SequenceManager(JiveConstants.OFFLINE, 1);
        new SequenceManager(JiveConstants.MUC_ROOM, 1);
        dbutils = new DbManager();
    }
    private PreparedStatement pstmt = null;
    private boolean success = false;
    private int type;
    private long currentID;
    private long maxID;
    private int blockSize;

    public SequenceManager(int seqType, int size) {
        managers.put(seqType, this);
        this.type = seqType;
        this.blockSize = size;
        currentID = 0l;
        maxID = 0l;
    }

    public static long nextID(int type) {
        if (managers.containsKey(type)) {
            return managers.get(type).nextUniqueID();
        } else {
            SequenceManager manager = new SequenceManager(type, 1);
            return manager.nextUniqueID();
        }
    }

    public static long nextID(Object o) {
        JiveID id = o.getClass().getAnnotation(JiveID.class);
        if (id == null) {
            throw new IllegalArgumentException(
                    "Annotation JiveID must be defined in the class "
                            + o.getClass());
        }

        return nextID(id.value());
    }

    public static void setBlockSize(int type, int blockSize) {
        if (managers.containsKey(type)) {
            managers.get(type).blockSize = blockSize;
        } else {
            new SequenceManager(type, blockSize);
        }
    }

    public synchronized long nextUniqueID() {
        if (!(currentID < maxID)) {
            getNextBlock(5);
        }
        long id = currentID;
        currentID++;
        return id;
    }

    private void getNextBlock(int count) {
        if (count == 0) {
            return;
        }
        try {
            pstmt = dbutils.getPreparedStatement(LOAD_ID);
            pstmt.setInt(1, type);
            ResultSet rs = pstmt.executeQuery();

            long currentID = 1;
            if (!rs.next()) {
                rs.close();
                pstmt.close();
                createNewID(type);
            } else {
                currentID = rs.getLong(1);
                rs.close();
                pstmt.close();
            }

            long newID = currentID + blockSize;
            pstmt = dbutils.getPreparedStatement(UPDATE_ID);
            pstmt.setLong(1, newID);
            pstmt.setInt(2, type);
            pstmt.setLong(3, currentID);
            success = pstmt.executeUpdate() == 1;
            if (success) {
                this.currentID = currentID;
                this.maxID = newID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!success) {
            try {
                Thread.sleep(75);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            getNextBlock(count - 1);
        }
    }

    private void createNewID(int type) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = dbutils.getPreparedStatement(CREATE_ID);
            pstmt.setInt(1, type);
            pstmt.execute();
        } finally {
            dbutils.closePreparedStatement(pstmt);
        }
    }
}