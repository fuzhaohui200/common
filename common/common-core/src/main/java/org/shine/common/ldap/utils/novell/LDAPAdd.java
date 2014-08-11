package org.shine.common.ldap.utils.novell;

import com.novell.ldap.*;

public class LDAPAdd {
    public static void add() {
        LDAPConnection ldap = new LDAPConnection();
        try {
            ldap.connect("202.192.18.251", 389);
            ldap.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");

            LDAPAttributeSet attributeSet = new LDAPAttributeSet();
            attributeSet.add(new LDAPAttribute("objectclass", new String(
                    "inetOrgPerson")));
            attributeSet.add(new LDAPAttribute("cn", new String("aaaaa")));
            attributeSet.add(new LDAPAttribute("givenname", new String[]{
                    "gfs", "gfds", "gfsd"}));
            attributeSet.add(new LDAPAttribute("sn", new String("gfsd")));
            attributeSet.add(new LDAPAttribute("eduPersonCardID", new String(
                    "2313463546")));
            attributeSet.add(new LDAPAttribute("mail", new String(
                    "fdasfa@Acme.com")));
            attributeSet.add(new LDAPAttribute("displayName",
                    new String("sfda")));

            LDAPEntry entry = new LDAPEntry("cn=aaaaa,ou=users,o=gzhu,dc=cn",
                    attributeSet);
            ldap.add(entry);
            ldap.disconnect();
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        LDAPAdd.add();
    }

}
