package org.shine.common.ldap.exchange;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPSearchResults;
import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.Blowfish;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.TimerTask;
import java.util.regex.Pattern;

public final class ScheduleSynchonizeMysql extends TimerTask {

    private String passwordKey;

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        Statement stmtQuery = null;
        Statement stmtUpdate = null;
        Statement stmtInsert = null;
        DbManager dbutils = null;
        try {
            Novell4LdapUtils utils = new Novell4LdapUtils();
            LDAPConnection ldapMain = new LDAPConnection();
            dbutils = new DbManager();
            ldapMain.connect("202.192.18.251", 389);
            ldapMain.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            LDAPSearchResults results = utils.search(ldapMain, "o=gzhu,dc=cn",
                    LDAPConnection.SCOPE_SUB, null, "cn=*");
            while (results != null && results.hasMore()) {
                stmtQuery = dbutils.getStatement();
                LDAPEntry entry = results.next();
                String cn = entry.getAttribute("cn").getStringValue();
                String passwordMain = entry.getAttribute("userPassword")
                        .getStringValue();

                String searchSql = "select username, plainPassword, encryptedPassword from ofUser where username ='"
                        + cn + "'";
                Blowfish blowfish = new Blowfish(passwordKey);
                String entryptedPasswordMain = blowfish
                        .decryptString(passwordMain);

                ResultSet rs = stmtQuery.executeQuery(searchSql);
                if (rs.next()) {
                    String entryptedPassword = rs
                            .getString("encryptedPassword");
                    if (!entryptedPasswordMain.equals(entryptedPassword)) {
                        stmtUpdate = dbutils.getStatement();
                        System.out
                                .println("===============================================");
                        String updateSql = "update  ofUser set encryptedPassword ='"
                                + entryptedPasswordMain
                                + "' where username ='"
                                + cn + "'";
                        stmtUpdate.executeUpdate(updateSql);
                        // conn.commit();
                        dbutils.closeStatement(stmtUpdate);
                    }
                } else {
                    stmtInsert = dbutils.getStatement();
                    if (Pattern.matches("^[0-9]{10}$", cn)) {
                        String realName = entry.getAttribute("sn")
                                .getStringValue()
                                + entry.getAttribute("givenName")
                                .getStringValue();
                        String name = new String(realName.getBytes(), "UTF-8");
                        String password = entry.getAttribute("userPassword")
                                .getStringValue();
                        String email = entry.getAttribute("mail")
                                .getStringValue();

                        String encryptedPassword = blowfish
                                .encryptString(password);
                        String date = "00" + new Date().getTime();

                        System.out.println(cn + ":" + password + ":" + email
                                + ":" + date + ":" + name);
                        String insertSql = "insert into ofUser(username, encryptedPassword,"
                                + "name, email, creationDate, modificationDate) values('"
                                + cn
                                + "', '"
                                + encryptedPassword
                                + "', '"
                                + name
                                + "', '"
                                + email
                                + "', '"
                                + date
                                + "', '" + date + "')";
                        stmtInsert.execute(insertSql);
                    } else {
                        /*
						 * 教职工插入
						 */
                    }
                    dbutils.closeStatement(stmtInsert);
                }
                dbutils.closeResultSet(rs);
                dbutils.closeStatement(stmtQuery);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

}
