package org.shine.common.ldap.utils.common;

import com.novell.ldap.*;

public class Novell4LdapUtils {

    public LDAPSearchResults search(LDAPConnection ldapConn, String base,
                                    int scope, String[] attrs, String filter) {
        LDAPSearchResults sr = null;
        try {
            LDAPSearchConstraints constraints = new LDAPSearchConstraints();
            constraints.setMaxResults(0);
            sr = ldapConn
                    .search(base, scope, filter, attrs, false, constraints);
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sr;
    }

    public void add(LDAPConnection ldapConn, LDAPAttributeSet set, String dn) {
        LDAPEntry entry = new LDAPEntry(dn, set);
        try {
            ldapConn.add(entry);
            ldapConn.disconnect();
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void modify(LDAPConnection ldapConn, String dn, int modifyOpt,
                       LDAPAttribute modifyAttr) {
        try {
            LDAPModification ldapModification = new LDAPModification(modifyOpt,
                    modifyAttr);
            ldapConn.modify(dn, ldapModification);
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void delete(LDAPConnection ldapConn, String dn) {
        try {
            ldapConn.delete(dn);
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
