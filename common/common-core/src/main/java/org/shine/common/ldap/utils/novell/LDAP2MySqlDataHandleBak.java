package org.shine.common.ldap.utils.novell;

import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.JNDI4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class LDAP2MySqlDataHandleBak {
    static Hashtable<String, Object> env = new Hashtable<String, Object>(11);

    public LDAP2MySqlDataHandleBak() {
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
    }

    public static String handleString(String str) {
        return str.substring(str.indexOf(":") + 1, str.length()).trim();
    }

    public static void main(String args[]) {
        System.out.println(new LDAP2MySqlDataHandleBak().getAllClassName(
                "dc=test,dc=com", "cn=*").size());
        // getAllClassMateRelationship("ou=users,o=gzhu,dc=cn", "cn=0605010*");

		/*--- 娣诲姞瀛﹂櫌鎵�湁鐝骇濂藉弸鍏崇郴 -----*/
        // makeFriends("ou=users,o=gzhu,dc=cn");
        // System.out.println(search("ou=users,o=gzhu,dc=cn", "cn=04160151*"));
        // System.out.println(countResult());

    }

    public Set getAllClassName(String base, String filter) {
        JNDI4LdapUtils utils = new JNDI4LdapUtils();
        Set set = new HashSet();
        NamingEnumeration results = utils.search(env, base, filter);
        while (results.hasMoreElements()) {
            SearchResult result;
            try {
                result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                NamingEnumeration allValues = attrs.getAll();
                String cn = attrs.get("cn").toString();
                if (Pattern.matches("^(cn: )[0-9]{10}$", cn)) {
                    String className = cn.substring(cn.indexOf(":") + 1,
                            cn.length() - 3).trim();
                    System.out.println("================================"
                            + className + "==================================");
                    set.add(className);
                }
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return set;
    }

    public List getAllClassMateRelationship(String base, String filter) {
        List list = new ArrayList();
        NamingEnumeration results = new JNDI4LdapUtils().search(env, base,
                filter);
        while (results.hasMoreElements()) {
            SearchResult result;
            try {
                result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                list.add(attrs);
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    public void makeFriends(String base) {
        DbManager dbHandle = new DbManager();
        Connection conn = dbHandle.getConnection();
        Set set = getAllClassName(base, "cn=*");
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            String filter = "cn=" + iter.next().toString() + "*";
            System.out.println("========================" + filter
                    + "==============================");
            List list = getAllClassMateRelationship(base, filter);
            System.out.println("========================" + list.size()
                    + "=========================");

            try {
                String sql = "insert into ofroster(rosterID, username, jid, sub, ask, recv, nick) values(?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    Attributes attrs = (Attributes) list.get(i);

                    String username = handleString(attrs.get("cn").toString());
                    System.out
                            .println("--------------------------------------------------");
                    System.out.println("username:" + username);

                    for (int j = 0; j < list.size(); j++) {
                        Attributes fattrs = (Attributes) list.get(j);
                        String jid = handleString(fattrs.get("cn").toString());
                        String nick = handleString(fattrs.get("givenName")
                                .toString());
                        if (!username.equals(jid)) {
                            System.out.println(username + ":" + jid + ":"
                                    + nick);
                            pstmt.setInt(1, ++count);
                            pstmt.setString(2, username);
                            pstmt.setString(3, jid + "@jabber.com");
                            pstmt.setInt(4, 3);
                            pstmt.setInt(5, -1);
                            pstmt.setInt(6, -1);
                            pstmt.setString(7, nick);
                            // pstmt.addBatch();
                        }
                    }
                    // pstmt.executeBatch();
                    System.out.println(count + "===========================");
                    // conn.commit();
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
