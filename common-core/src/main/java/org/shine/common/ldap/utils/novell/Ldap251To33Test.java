package org.shine.common.ldap.utils.novell;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import org.junit.Test;
import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Ldap251To33Test {

    @Test
    public void synchonizeLdap() {
        Novell4LdapUtils utils = new Novell4LdapUtils();
        LDAPConnection ldap = new LDAPConnection();

        DbManager dbHandle = new DbManager();
        Connection conn = dbHandle.getConnection();
        try {
            ldap.connect("202.192.18.251", 389);
            ldap.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            LDAPSearchResults sr = utils.search(ldap, "o=gzhu,dc=cn",
                    LDAPConnection.SCOPE_SUB, null, "cn=*");

            // PreparedStatement pstmt = null;
            Statement stmt = conn.createStatement();
            Set groupNames = new HashSet();

            while (sr != null && sr.hasMore()) {
                LDAPEntry entry = sr.next();
                String username = entry.getAttribute("cn").getStringValue();

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
                    String name = entry.getAttribute("givenName")
                            .getStringValue();
                    String email = entry.getAttribute("userPassword")
                            .getStringValue();
                    String password = entry.getAttribute("mail")
                            .getStringValue();
                    String date = new Date().toLocaleString();
                    String today = date.substring(0, date.indexOf(" "));
                    String ofUserSql = "insert into ofUser(username, plainPassword, "
                            + "name, email, creationDate, modificationDate) values('"
                            + username
                            + "', '"
                            + password
                            + "', '"
                            + name
                            + "', '"
                            + email
                            + "', '"
                            + today
                            + "', '"
                            + today
                            + "')";
                    stmt.addBatch(ofUserSql);
                    stmt.executeBatch();
                    System.out.println(groupName + ":" + username + ":" + name
                            + ":" + email + ":" + password);
                } else {

                }
            }

        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testDate() {
        String date = new Date().toLocaleString();
        System.out.println(date.substring(0, date.indexOf(" ")).length());
    }

}
