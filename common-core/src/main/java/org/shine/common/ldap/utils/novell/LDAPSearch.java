package org.shine.common.ldap.utils.novell;

import com.novell.ldap.*;

import java.util.HashSet;
import java.util.Set;

public class LDAPSearch {

    public static void search() {
        LDAPConnection ldap = new LDAPConnection();
        try {

            // String myBaseDN = "cn=group,DC=EXAMPLE,DC=LAB,DC=LOCAL" ; String
            // myFilter= "(objectclass=groupOfNames)" ; LDAPConnection myConn =
            // new LDAPConnection(); Integer newLimit = new Integer(0); try {
            // myConn.setOption(LDAPv3.SIZELIMIT, newLimit); } catch
            // (LDAPException e) { System.out.println( "Unable to handle" ); }

            ldap.connect("202.192.18.251", 389);
            LDAPSearchConstraints constraints = new LDAPSearchConstraints();
            constraints.setMaxResults(0);

            ldap.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");

            // LDAPSearchResults rs = ldap.search("ou=users,o=gzhu,dc=cn",
            // LDAPConnection.SCOPE_SUB, "cn=*", null, false);
            LDAPSearchResults rs = ldap.search("o=gzhu,dc=cn",
                    LDAPConnection.SCOPE_SUB, "departmentNumber=83", null,
                    false, constraints);

            int count = 0;
            System.out
                    .println("----------------------------------------------------------------");
            Set set = new HashSet();
            while (rs.hasMore()) {
                LDAPEntry entry = rs.next();

                // System.out.println(entry.getDN());
                // System.out.println(entry.getAttribute("eduPersonCardID"));
                // System.out.println(entry.getAttribute("mail"));
                // System.out.println(entry.getAttribute("userPassword").getStringValue());
                // System.out.println(entry.getAttribute("cn").getStringValue());
                LDAPAttribute password = entry.getAttribute("departmentNumber");
                // System.out.println(password.getStringValue());
                if (password != null
                        && !set.contains(password.getStringValue())) {
                    System.out.println(password.getStringValue());
                    set.add(password.getStringValue());
                    count++;

                }
                // System.out.println(entry.getAttributeSet());
                //
                // System.out.println( password.getStringValue());
                // System.out.println(entry.getAttributeSet());
                // System.out
                // .println("----------------------------------------------------------------");

            }
            System.out.println("共" + count + "条记录");
        } catch (LDAPException e) {

            System.err.print("连接失败");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        LDAPSearch.search();
    }

}
