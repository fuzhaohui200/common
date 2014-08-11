package org.shine.common.ldap.exchange;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import org.shine.common.ldap.relationship.MakeRelationShip;
import org.shine.common.ldap.utils.common.JiveConstants;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;
import org.shine.common.ldap.utils.common.SequenceManager;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class NovellMakeRelationShip implements INovellMakeRelationShip {
    private LDAPConnection ldapConn = null;
    private Novell4LdapUtils utils = null;

    public NovellMakeRelationShip() {
        init();
    }

    public static void main(String args[]) {
        // new MakeRelationShip().makeClassmate("o=gzhu,dc=cn", "cn=0911020*");

        new NovellMakeRelationShip().makeFriends("o=gzhu,dc=cn");

    }

    @SuppressWarnings("deprecation")
    private void init() {
        ldapConn = new LDAPConnection();
        utils = new Novell4LdapUtils();
        Properties config = new Properties();
        InputStream in = NovellMakeRelationShip.class
                .getResourceAsStream("novell.properties");
        try {
            config.load(in);
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

    private Set<String> getAllClassName(String base) {
        Set<String> set = new HashSet<String>();
        LDAPSearchResults results = utils.search(ldapConn, base,
                LDAPConnection.SCOPE_SUB, new String[]{"cn"}, "cn=*");
        try {
            while (results.hasMore()) {
                LDAPEntry entry;
                entry = results.next();
                String cn = entry.getAttribute("cn").getStringValue();
                if (Pattern.matches("^[0-9]{10}$", cn)) {
                    String className = cn.substring(0, cn.length() - 3);
                    String filter = "cn=" + className + "*";
                    set.add(filter);
                }
            }
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return set;
    }

    private List<LDAPEntry> makeClassmate(String base, String filter) {
        List<LDAPEntry> list = new ArrayList<LDAPEntry>();
        LDAPSearchResults results = utils.search(ldapConn, base,
                LDAPConnection.SCOPE_SUB, new String[]{"cn", "sn",
                        "givenName"}, filter);
        try {
            while (results.hasMore()) {
                LDAPEntry entry = results.next();
                list.add(entry);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings("rawtypes")
    public void makeFriends(String base) {
        Set set = this.getAllClassName(base);

        long rosterID = SequenceManager.nextID(JiveConstants.ROSTER);
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            String filter = (String) iter.next();
            List list = makeClassmate(base, filter);
            for (int i = 0; i < list.size(); i++) {
                LDAPEntry entryMain = (LDAPEntry) list.get(i);
                String username = entryMain.getAttribute("cn").getStringValue();
                for (int j = 0; j < list.size(); j++) {
                    LDAPEntry entryAssist = (LDAPEntry) list.get(j);
                    String jid = entryAssist.getAttribute("cn")
                            .getStringValue();
                    String realName = entryAssist.getAttribute("sn")
                            .getStringValue()
                            + entryAssist.getAttribute("givenName")
                            .getStringValue();
                    if (!username.equals(jid)) {
                        String jidName = jid + "@jabber.com";
                        MakeRelationShip
                                .createItem(username, jidName, realName);
                        System.out.println(username + ":" + jid + ":"
                                + realName + ":" + rosterID);
                    }
                }
            }
        }
    }

}
