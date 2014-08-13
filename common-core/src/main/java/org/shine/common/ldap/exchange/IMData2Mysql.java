package org.shine.common.ldap.exchange;

import com.novell.ldap.*;
import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.Blowfish;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class IMData2Mysql implements IIMData2Mysql {

    private LDAPConnection ldapConn = null;
    private Properties config = null;
    private DbManager dbutils = null;

    public IMData2Mysql() {
        init();
    }

    public static void main(String args[]) {
        new IMData2Mysql().synchonizeLdap2Mysql("123456");
        // new IMData2Mysql().fromIMModifyDataMysql("0603030045", "123456",
        // "654321");

    }

    @SuppressWarnings("deprecation")
    private void init() {
        config = new Properties();
        dbutils = new DbManager();
        try {
            config.load(IMData2Mysql.class
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
     * @see important.IIMData2Mysql#synchonizeLdap2Mysql(java.lang.String)
     */
    public void synchonizeLdap2Mysql(String passwordKey) {
        Statement stmt = dbutils.getStatement();
        Novell4LdapUtils utils = new Novell4LdapUtils();
        try {

            LDAPSearchResults sr = utils.search(ldapConn,
                    "ou=users,o=gzhu,dc=cn", LDAPConnection.SCOPE_SUB, null,
                    "cn=*");
            Set<String> groupNames = new HashSet<String>();

            while (sr != null && sr.hasMore()) {
                LDAPEntry entry = sr.next();
                String username = entry.getAttribute("cn").getStringValue();

                if (Pattern.matches("^[0-9]{6}$|^[0-9]{10}$", username)) {
                    String realName = entry.getAttribute("sn").getStringValue()
                            + entry.getAttribute("givenName").getStringValue();
                    String name = new String(realName.getBytes(), "UTF-8");
                    String password = entry.getAttribute("userPassword")
                            .getStringValue();
                    String email = entry.getAttribute("mail").getStringValue();

                    Blowfish encrypted = new Blowfish(passwordKey);
                    String encryptedPassword = encrypted
                            .encryptString(password);
                    String date = "00" + new Date().getTime();

                    String ofUserSql = "insert into ofUser(username, encryptedPassword"
                            + ",name, email, creationDate, modificationDate) values('"
                            + username
                            + "', '"
                            + encryptedPassword
                            + "', '"
                            + name
                            + "', '"
                            + email
                            + "', '"
                            + date
                            + "', '"
                            + date + "')";

                    stmt.addBatch(ofUserSql);

                    if (Pattern.matches("^[0-9]{10}$", username)) {
                        String groupName = username.substring(0,
                                username.length() - 3);
                        if (!groupNames.contains(groupName)) {
                            groupNames.add(groupName);
                            String ofGroupSql = "insert into ofGroup(groupName) values('"
                                    + groupName + "')";
                            stmt.addBatch(ofGroupSql);
                        }
                        String ofGroupUserSql = "insert into ofGroupUser(groupName, username, administrator) values('"
                                + groupName + "','" + username + "'," + 1 + ")";
                        stmt.addBatch(ofGroupUserSql);
                    } else if (Pattern.matches("^[0-9]{6}$", username)) {
                        System.out.println(username + ":" + name + ":" + email
                                + ":" + password);
                    }

                }
                stmt.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbutils.closeStatement(stmt);
        dbutils.closeConnection();
    }

    /*
     * (non-Javadoc)
     *
     * @see important.IIMData2Mysql#fromIMModifyDataMysql(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public void fromIMModifyDataMysql(String cn, String passwordKey,
                                      String password) {
        String sql = "update ofUser set encryptedPassword = ? where username = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = dbutils.getPreparedStatement(sql);
            Blowfish encrypted = new Blowfish(passwordKey);
            pstmt.setString(1, encrypted.encryptString(password));
            pstmt.setString(2, cn);
            pstmt.executeUpdate();
            String dn = "cn=" + cn + ",ou=users,o=gzhu,dc=cn";
            System.out.println(dn);
            LDAPAttribute modifyAttr = new LDAPAttribute("userPassword",
                    password);
            new Novell4LdapUtils().modify(ldapConn, dn,
                    LDAPModification.REPLACE, modifyAttr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbutils.closePreparedStatement(pstmt);
        dbutils.closeConnection();
    }
}
