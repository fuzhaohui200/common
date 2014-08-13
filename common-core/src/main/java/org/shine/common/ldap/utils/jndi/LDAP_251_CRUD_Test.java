package org.shine.common.ldap.utils.jndi;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import org.junit.Test;
import org.shine.common.ldap.utils.common.JNDI4LdapUtils;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

public class LDAP_251_CRUD_Test {

    @Test
    public void testSearch() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env, "o=gzhu,dc=cn",
                "cn=*");
        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                System.out.println(sr.getNameInNamespace());
                Attributes attrs = sr.getAttributes();
                System.out.println(attrs.get("userPassword"));
                count++;
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
    }

    @Test
    public void testNovellSearch() {
        LDAPConnection ldapConn = new LDAPConnection();

        int count = 0;
        try {
            ldapConn.connect("202.192.18.251", 389);
            ldapConn.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            LDAPSearchResults results = new Novell4LdapUtils().search(ldapConn,
                    "o=gzhu, dc=cn", LDAPConnection.SCOPE_SUB,
                    new String[]{"userPassword"}, "cn=*");
            while (results.hasMore() && results != null) {
                LDAPEntry entry = results.next();
                System.out.println(entry.getAttribute("userPassword"));
            }
        } catch (LDAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
    }

    @Test
    public void testModify1() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "ou=users,o=gzhu,dc=cn", "cn=*");
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String dn = sr.getNameInNamespace();
                System.out.println(dn);
                Attribute attr = new BasicAttribute("ou");
                Attributes attrs = new BasicAttributes();
                attrs.put(attr);
                new JNDI4LdapUtils().modify(env, dn,
                        DirContext.REMOVE_ATTRIBUTE, attrs);
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testAdd() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        String newUserName = "88888888";
        BasicAttributes attrsbu = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectclass");
        objclassSet.add("organizationalPerson");
        objclassSet.add("top");
        objclassSet.add("inetOrgPerson");
        objclassSet.add("person");
        attrsbu.put(objclassSet);
        attrsbu.put("cn", newUserName);
        attrsbu.put("sn", "112");
        String DNN = "cn=" + newUserName + ","
                + "ou=Admin,o=account,o=gzhu,dc=cn";

        new JNDI4LdapUtils().add(env, DNN, attrsbu);
    }

    @Test
    public void testModify() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        String dn = "cn=200910100001,ou=Admin,o=account,o=gzhu,dc=cn";
        Attribute attr = new BasicAttribute("givenName");
        Attributes attrs = new BasicAttributes();
        attrs.put(attr);
        new JNDI4LdapUtils()
                .modify(env, dn, DirContext.REMOVE_ATTRIBUTE, attrs);

    }

    @Test
    public void testDelete() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        String dn = "cn=88888888,ou=Admin,o=account,o=gzhu,dc=cn";
        new JNDI4LdapUtils().delete(env, dn);

    }

}
