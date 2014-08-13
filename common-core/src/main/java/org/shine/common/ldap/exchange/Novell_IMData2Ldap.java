package org.shine.common.ldap.exchange;

import com.novell.ldap.*;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import javax.naming.directory.DirContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class Novell_IMData2Ldap {
    private Properties config = null;

    public Novell_IMData2Ldap() {
        init();
    }

    public static void main(String argsp[]) {
        new Novell_IMData2Ldap().novell_synchonizeLdap2Ldap();
    }

    private void init() {
        try {
            config = new Properties();
            config.load(Novell_IMData2Ldap.class
                    .getResourceAsStream("novell.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void novell_synchonizeLdap2Ldap() {

        Novell4LdapUtils utils = new Novell4LdapUtils();
        try {
            LDAPConnection ldapConnMain = new LDAPConnection();
            ldapConnMain.connect(config.getProperty("novellhost"),
                    Integer.parseInt(config.getProperty("novellport")));
            ldapConnMain.bind(LDAPConnection.LDAP_V3,
                    config.getProperty("novelldn"),
                    config.getProperty("novellpass"));

            LDAPSearchResults rs = utils.search(ldapConnMain,
                    "ou=users,o=gzhu,dc=cn", LDAPConnection.SCOPE_SUB, null,
                    "cn=*");

            LDAPConnection ldapConnTest = new LDAPConnection();
            ldapConnTest.connect(config.getProperty("testnovellhost"),
                    Integer.parseInt(config.getProperty("testnovellport")));
            ldapConnTest.bind(LDAPConnection.LDAP_V3,
                    config.getProperty("testnovelldn"),
                    config.getProperty("testnovellpass"));

            LDAPAttributeSet attrs = new LDAPAttributeSet();
            attrs.add(new LDAPAttribute("objectclass", "groupOfNames"));
            attrs.add(new LDAPAttribute("objectclass", "top"));

            Set<String> set = new HashSet<String>();

            while (rs.hasMore() && rs != null) {
                LDAPEntry entry = rs.next();
                LDAPAttributeSet attrsVal = entry.getAttributeSet();
                String cn = entry.getAttribute("cn").getStringValue();
                String DN = entry.getDN();

                if (Pattern.matches("^[0-9]{10}$", cn)) {

                    String className = cn.substring(0, 7);

                    attrs.add(new LDAPAttribute("ou", className));
                    String dn = "ou=" + className + ",dc=test,dc=com";
                    String childDN = "cn=" + cn + ",ou=" + className
                            + ",dc=test,dc=com";

                    if (!set.contains(className)) {
                        set.add(className);
                        attrs.add(new LDAPAttribute("member", childDN));
                        utils.add(ldapConnTest, attrs, dn);
                    } else {

                        LDAPAttribute addMember = new LDAPAttribute("member",
                                childDN);
                        utils.modify(ldapConnTest, dn, LDAPModification.ADD,
                                addMember);
                    }

                    utils.add(ldapConnTest, attrsVal, childDN);
                } else if (Pattern.matches("^[0-9]{6}$", cn)) {
                    String departmentName = DN.substring(DN.indexOf("ou=") + 3,
                            DN.indexOf("o=") - 1);
                    String childDN = "";

                    if (!departmentName.equals("users")) {
                        String dn = "ou=" + departmentName + ",dc=test,dc=com";
                        childDN = "cn=" + cn + ",ou=" + departmentName
                                + ",dc=test,dc=com";

                        if (!set.contains(departmentName)) {
                            set.add(departmentName);
                            attrs.add(new LDAPAttribute("member", childDN));
                            attrs.add(new LDAPAttribute("cn", departmentName));
                            attrs.add(new LDAPAttribute("ou", departmentName));
                            utils.add(ldapConnTest, attrs, dn);
                        } else {
                            LDAPAttribute addMember = new LDAPAttribute(
                                    "member", childDN);
                            utils.modify(ldapConnTest, dn,
                                    DirContext.ADD_ATTRIBUTE, addMember);
                        }
                    } else {
                        String departmentNumber = entry.getAttribute(
                                "departmentNumber").getStringValue();
                        // String departName = entry.getAttribute("displayName")
                        // .getStringValue();

                        String dn = "ou=" + departmentNumber
                                + ",dc=test,dc=com";
                        childDN = "cn=" + cn + ",ou=" + departmentNumber
                                + ",dc=test,dc=com";
                        if (!set.contains(departmentNumber)) {
                            set.add(departmentNumber);
                            attrs.add(new LDAPAttribute("member", childDN));
                            attrs.add(new LDAPAttribute("cn", departmentName));
                            attrs.add(new LDAPAttribute("ou", departmentName));
                            utils.add(ldapConnTest, attrs, dn);
                        } else {
                            LDAPAttribute addMember = new LDAPAttribute(
                                    "member", childDN);
                            utils.modify(ldapConnTest, dn,
                                    LDAPModification.ADD, addMember);
                        }
                    }
                    utils.add(ldapConnTest, attrsVal, childDN);
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void ModifyDataLdap(String cn, String password) {
        try {
            Novell4LdapUtils utils = new Novell4LdapUtils();
            LDAPConnection ldapMain = new LDAPConnection();
            ldapMain.connect("202.192.18.251", 389);
            ldapMain.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            String dn = "cn=" + cn + ",ou=users,o=gzhu,dc=cn";
            LDAPAttribute modifyAttr = new LDAPAttribute("userPassword",
                    password);
            utils.modify(ldapMain, dn, LDAPModification.REPLACE, modifyAttr);
            LDAPConnection ldapAssist = new LDAPConnection();
            ldapAssist.connect("202.192.21.33", 389);
            ldapAssist.bind(LDAPConnection.LDAP_V3, "cn=root,dc=test,dc=com",
                    "123456");
            utils.modify(ldapAssist, dn, LDAPModification.REPLACE, modifyAttr);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

}
