package org.shine.common.ldap.utils.novell;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

public class LDAPDelete {

    public static void delete() {
        LDAPConnection ldap = new LDAPConnection();

        try {
            ldap.connect("202.192.18.251", 389);
            ldap.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            ldap.delete("cn=123456,ou=users,o=gzhu,dc=cn");
            System.out.println("删除成功!!!!!!!!!");
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        LDAPDelete.delete();
    }

}
