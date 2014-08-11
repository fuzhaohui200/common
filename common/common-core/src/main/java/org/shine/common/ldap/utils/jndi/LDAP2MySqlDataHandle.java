package org.shine.common.ldap.utils.jndi;

import org.shine.common.ldap.db.DbManager;
import org.shine.common.ldap.utils.common.JNDI4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

public class LDAP2MySqlDataHandle {

    static Hashtable<String, Object> env = new Hashtable<String, Object>(11);

    public LDAP2MySqlDataHandle() {
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
        // System.out.println(new LDAP2MySqlDataHandle().getAllClassName(
        // "dc=test,dc=com", "cn=*").size());
        // getAllClassMateRelationship("ou=users,o=gzhu,dc=cn", "cn=0605010*");

        new LDAP2MySqlDataHandle().makeFriends("dc=test,dc=com");
        // System.out.println(search("ou=users,o=gzhu,dc=cn", "cn=04160151*"));
        // System.out.println(countResult());

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
                String cn = attrs.get("cn").toString();
                if (cn.length() > 11) {
                    list.add(attrs);
                }
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    public void makeFriends(String base) {
        JNDI4LdapUtils utils = new JNDI4LdapUtils();
        DbManager dbHandle = new DbManager();
        Connection conn = dbHandle.getConnection();
        NamingEnumeration ne = utils.search(env, base, "ou=*");
        try {
            String sql = "insert into ofRoster(rosterID, username, jid, sub, ask, recv, nick) values(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int count = 0;
            while (ne.hasMore()) {
                SearchResult sr = (SearchResult) ne.next();
                String dn = sr.getNameInNamespace();
                String[] strs = dn.split(",");
                String ou = strs[0].substring(3, strs[0].length());
                List list = new ArrayList();
                if (Pattern.matches("^[0-9]{7}$", ou)) {
                    list = getAllClassMateRelationship(dn, "cn=*");
                }
                for (int i = 0; i < list.size(); i++) {
                    Attributes attrs = (Attributes) list.get(i);
                    String username = handleString(attrs.get("cn").toString());
                    for (int j = 0; j < list.size(); j++) {
                        Attributes fattrs = (Attributes) list.get(j);
                        String jid = handleString(fattrs.get("cn").toString());
                        // String realName =
                        // entry.getAttribute("sn").getStringValue()+entry.getAttribute("givenName").getStringValue();
                        String realName = handleString(fattrs.get("sn")
                                .toString())
                                + handleString(fattrs.get("givenname")
                                .toString());
                        String name = new String(realName.getBytes(), "UTF-8");
                        if (!username.equals(jid)) {
                            System.out.println(username + ":" + jid + ":"
                                    + realName);
                            pstmt.setInt(1, ++count);
                            pstmt.setString(2, username);
                            pstmt.setString(3, jid + "@jabber.com");
                            pstmt.setInt(4, 3);
                            pstmt.setInt(5, -1);
                            pstmt.setInt(6, -1);
                            pstmt.setString(7, realName);
                            pstmt.addBatch();
                        }
                    }
                    pstmt.executeBatch();
                    // conn.commit();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
