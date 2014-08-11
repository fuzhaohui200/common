package org.shine.common.ldap.utils.jndi;

import org.junit.Test;
import org.shine.common.ldap.utils.common.JNDI4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;

public class LDAP_33_CRUD_Test {
    @Test
    public void testSearch() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "cn=*");
        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                System.out.println(sr.getNameInNamespace());
                count++;
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    @Test
    public void testLink() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        // String dn="cn=88888888,ou=Admin,o=account,o=gzhu,dc=cn";
        System.out.println(new JNDI4LdapUtils().search(env, "dc=test,dc=com",
                "cn=*"));

    }

    @Test
    public void testSearch2() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "ou=0513094");

        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                System.out.println(sr.getNameInNamespace());
                Attributes attrs = sr.getAttributes();
                System.out.println(attrs.toString());
                System.out.println(attrs.get("member"));
                count++;
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
        // NamingEnumeration ne = new LdapUtils().search(base, filter);
    }

    @Test
    public void testSearchTo33() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "cn=*");

        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                Attributes attrs = sr.getAttributes();
                if (attrs.get("st") != null) {
                    count++;
                }
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
        // NamingEnumeration ne = new LdapUtils().search(base, filter);
    }

    @Test
    public void testModify() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        int count = 0;
        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "ou=*");
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String dn = sr.getNameInNamespace();
                System.out.println(dn);
                Attribute attr = new BasicAttribute("st");
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
    public void testModify1() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        int count = 0;
        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "ou=8888889");
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String dn = sr.getNameInNamespace();
                System.out.println(dn);
                BasicAttributes attrs = new BasicAttributes();
                BasicAttribute memberAttr = new BasicAttribute("member");
                memberAttr.add("cn=0416015102,ou=0416015,dc=test,dc=com");
                // memberAttr.add("cn=0416015122,ou=0416015,dc=test,dc=com");
                // memberAttr.add("cn=0416015122,ou=0416015,dc=test,dc=com");
                attrs.put(memberAttr);
                new JNDI4LdapUtils().modify(env, dn, DirContext.ADD_ATTRIBUTE,
                        attrs);
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testModify2AddMember() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        String dn = "ou=0513094,dc=test,dc=com";
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute attr = new BasicAttribute("member");
        attr.add("0513094110");
        attr.add("0513094134");
        attr.add("0513094144");
        attrs.put(attr);
        new JNDI4LdapUtils().modify(env, dn, DirContext.ADD_ATTRIBUTE, attrs);
    }

    @Test
    public void testSearch2Redhat() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        NamingEnumeration ne = new JNDI4LdapUtils().search(env,
                "dc=test,dc=com", "ou=*");

        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String dn = sr.getNameInNamespace();
                if (dn.length() < 26) {
                    count++;
                    System.out.println(dn);
                }
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
        // NamingEnumeration ne = new LdapUtils().search(base, filter);
    }

    @Test
    public void testAdd() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
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
        String DNN = "ou=" + newUserName + "," + "dc=test,dc=com";

        new JNDI4LdapUtils().add(env, DNN, attrsbu);

    }

    /**
     * 鍒嗙粍鍗曚釜娣诲姞娴嬭瘯
     */
    @Test
    public void testAdd1() {
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        String newUserName = "8888889";
        BasicAttributes attrsbu = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectclass");
        objclassSet.add("groupOfNames");
        objclassSet.add("top");
        attrsbu.put(objclassSet);
        BasicAttribute memberAttr = new BasicAttribute("member");
        memberAttr.add("cn=0416015102,ou=0416015,dc=test,dc=com");
        memberAttr.add("cn=0416015122,ou=0416015,dc=test,dc=com");
        memberAttr.add("cn=0416015122,ou=0416015,dc=test,dc=com");
        attrsbu.put(memberAttr);
        attrsbu.put("cn", newUserName);
        // attrsbu.put("ou", "8888888");
        String DNN = "ou=" + newUserName + "," + "dc=test,dc=com";

        new JNDI4LdapUtils().add(env, DNN, attrsbu);

    }

}
