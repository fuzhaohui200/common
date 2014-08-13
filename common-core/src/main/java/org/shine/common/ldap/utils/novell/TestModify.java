package org.shine.common.ldap.utils.novell;

import com.novell.ldap.LDAPConnection;
import org.shine.common.ldap.db.DbManager;

import java.sql.PreparedStatement;
import java.util.Properties;

public class TestModify {

    private LDAPConnection ldapConn = null;
    private Properties config = null;
    private DbManager dbHandle = null;

    public TestModify() {
        init();
    }

    public static void main(String[] args) {
        new TestModify().fromIMModifyDataMysql("0923010169", "好");
    }

    private void init() {
        config = new Properties();
        dbHandle = new DbManager();
        try {
            config.load(TestModify.class
                    .getResourceAsStream("novell.properties"));
            ldapConn = new LDAPConnection();
            ldapConn.connect(config.getProperty("novellhost"),
                    Integer.parseInt(config.getProperty("novellport")));
            ldapConn.bind(LDAPConnection.LDAP_V3,
                    config.getProperty("novelldn"),
                    config.getProperty("novellpass"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see important.IIMData2Mysql#fromIMModifyDataMysql(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public void fromIMModifyDataMysql(String cn, String name) {
        String sql = "update ofUser set name = ? where username = ?";
        try {
            PreparedStatement pstmt = dbHandle.getPreparedStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, cn);
            pstmt.executeUpdate();
            String dn = "cn=" + cn + ",ou=users,o=gzhu,dc=cn";
            // System.out.println(dn);
            // LDAPAttribute modifyAttr = new LDAPAttribute("userPassword",
            // password);
            // new Novell4LdapUtils().modify(ldapConn, dn,
            // LDAPModification.REPLACE, modifyAttr);
            pstmt.close();
            dbHandle.getConnection().close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
