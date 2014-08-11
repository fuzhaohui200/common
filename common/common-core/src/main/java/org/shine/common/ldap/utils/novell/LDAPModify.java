package org.shine.common.ldap.utils.novell;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;

public class LDAPModify {
    private static final int LDAP_VERSION = LDAPConnection.LDAP_V3;
    private static final int LDAP_PORT = 389; // Integer.parseInt(s)
    private static final String HOST = "202.192.18.251";
    private static final String LOGIN_DN = "cn=Admin,o=gzhu,dc=cn";
    private static final String LOGIN_PASS = "gzhu_admin";

    private String entryDN;
    private String trusteeDN;

    public static void modify() {

        String dn = "cn=0603030045,ou=users,o=gzhu,dc=cn";

        LDAPConnection ldap = new LDAPConnection();
        try {

            ldap.connect(HOST, LDAP_PORT);
            ldap.bind(LDAP_VERSION, "cn=Admin,o=gzhu,dc=cn", LOGIN_PASS);
            // LDAPSearchResults rs =
            // ldap.search(dn,LDAPConnection.SCOPE_SUB,"cn=0603030045",null,false);
            // LDAPEntry entry = rs.next();
            // String modifyString = "fuzhaohui200@gmail.com";
            LDAPAttribute attr = new LDAPAttribute("userPassword", "654321");
            LDAPModification modify = new LDAPModification(
                    LDAPModification.REPLACE, attr);
            ldap.modify(dn, modify);
            LDAPSearch.search();
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        LDAPModify.modify();
    }

}
