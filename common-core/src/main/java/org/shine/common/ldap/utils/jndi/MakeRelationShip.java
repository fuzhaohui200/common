package org.shine.common.ldap.utils.jndi;

import com.novell.ldap.LDAPException;
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

public class MakeRelationShip {
    Hashtable<String, Object> env = new Hashtable<String, Object>(11);

    public MakeRelationShip() {
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
    }

    public static void main(String args[]) {
        // new MakeRelationShip().makeClassmate("o=gzhu,dc=cn", "cn=0911020*");

        new MakeRelationShip().makeFriends("o=gzhu,dc=cn");

    }

    /**
     * 鑾峰彇鐝骇鐨勫ソ鍙嬪叧绯�
     *
     * @param base鏍     �
     * @param filter杩囨护
     * @return
     * @throws LDAPException
     * @throws NamingException
     */
    public List makeClassmate(String base, String filter) {
        JNDI4LdapUtils utils = utils = new JNDI4LdapUtils();
        List list = new ArrayList();
        NamingEnumeration results = utils.search(env, base, filter);
        try {
            while (results.hasMore()) {
                SearchResult rs = (SearchResult) results.next();
                Attributes attrs = rs.getAttributes();
                list.add(attrs);
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public void makeFriends(String base) {
        JNDI4LdapUtils utils = new JNDI4LdapUtils();
        DbManager dbHandle = new DbManager();
        Connection conn = dbHandle.getConnection();
        NamingEnumeration results = utils.search(env, base, "cn=*");
        try {
            String sql = "insert into ofRoster(rosterID, username, jid, sub, ask, recv, nick) values(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int count = 0;
            while (results.hasMore()) {
                SearchResult rs = (SearchResult) results.next();
                Attributes mainAttrs = rs.getAttributes();
                String cn = utils.handleString(mainAttrs.get("cn").toString());
                if (Pattern.matches("^[0-9]{10}$", cn)) {
                    String className = cn.substring(0, cn.length() - 3);
                    String filter = "cn=" + cn + "*";
                    List list = makeClassmate(base, filter);
                    for (int i = 0; i < list.size(); i++) {
                        Attributes attrs = (Attributes) list.get(i);
                        String username = utils.handleString(attrs.get("cn")
                                .toString());
                        for (int j = 0; j < list.size(); j++) {
                            Attributes fattrs = (Attributes) list.get(j);
                            String jid = utils.handleString(fattrs.get("cn")
                                    .toString());
                            String realName = utils.handleString(fattrs.get(
                                    "sn").toString())
                                    + utils.handleString(fattrs
                                    .get("givenname").toString());
                            String name = new String(realName.getBytes(),
                                    "UTF-8");
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
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
