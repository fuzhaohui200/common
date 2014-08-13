package org.shine.common.ldap.utils.common;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import java.util.Hashtable;

public class JNDI4LdapUtils {

    @SuppressWarnings("rawtypes")
    public NamingEnumeration search(Hashtable env, String base, String filter) {
        NamingEnumeration results = null;
        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            results = ctx.search(base, filter, sc);
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return results;
    }

    @SuppressWarnings("rawtypes")
    public void add(Hashtable env, String dn, Attributes attr) {
        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            ctx.createSubcontext(dn, attr);
            // ctx.bind(dn, attr);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public void modify(Hashtable env, String dn, int modOp, Attributes attrs) {
        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            ctx.modifyAttributes(dn, modOp, attrs);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public void delete(Hashtable env, String dn) {
        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
            // ctx.unbind(dn);
            ctx.destroySubcontext(dn);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String handleString(String str) {
        return str.substring(str.indexOf(":") + 1, str.length()).trim();
    }

}