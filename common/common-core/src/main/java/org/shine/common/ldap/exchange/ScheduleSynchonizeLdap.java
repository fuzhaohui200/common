package org.shine.common.ldap.exchange;

import com.novell.ldap.*;
import org.shine.common.ldap.utils.common.Novell4LdapUtils;

import java.util.TimerTask;
import java.util.regex.Pattern;

public final class ScheduleSynchonizeLdap extends TimerTask {

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        try {
            Novell4LdapUtils utils = new Novell4LdapUtils();
            LDAPConnection ldapMain = new LDAPConnection();
            ldapMain.connect("202.192.18.251", 389);
            ldapMain.bind(LDAPConnection.LDAP_V3, "cn=Admin,o=gzhu,dc=cn",
                    "gzhu_admin");
            LDAPSearchResults results = utils.search(ldapMain, "o=gzhu,dc=cn",
                    LDAPConnection.SCOPE_SUB, null, "cn=*");

            LDAPConnection ldapAssist = new LDAPConnection();
            ldapAssist.connect("202.192.21.33", 389);
            ldapAssist.bind(LDAPConnection.LDAP_V3, "cn=root,dc=test,dc=com",
                    "123456");

            while (results != null && results.hasMore()) {
                LDAPEntry entry = results.next();
                String cn = entry.getAttribute("cn").getStringValue();
                if (Pattern.matches("^[0-9]{10}$", cn)) {
                    String passwordMain = entry.getAttribute("userPassword")
                            .getStringValue();

                    LDAPSearchResults resultsAssist = utils.search(ldapAssist,
                            "dc=test,dc=com", LDAPConnection.SCOPE_SUB,
                            new String[]{"userPassword"}, "cn=" + cn);
                    if (resultsAssist != null && resultsAssist.hasMore()) {
                        LDAPEntry entryAssist = resultsAssist.next();
                        String passwordAssist = entryAssist.getAttribute(
                                "userPassword").getStringValue();
                        System.out.println(cn + ":" + passwordMain + ":"
                                + passwordAssist);
                        if (!passwordAssist.equals(passwordMain)) {
                            LDAPAttribute modifyAttr = new LDAPAttribute(
                                    "userPassword", passwordMain);
                            String dn = entryAssist.getDN();
                            System.out.println(dn);
                            utils.modify(ldapAssist, dn,
                                    LDAPModification.REPLACE, modifyAttr);
                        }
                    } else {
                        if (Pattern.matches("^[0-9]{10}$", cn)) {
                            String childDN = "cn=" + cn + ",ou="
                                    + cn.substring(0, 7) + ",dc=test,dc=com";
                            LDAPAttributeSet attrSet = entry.getAttributeSet();
                            utils.add(ldapAssist, attrSet, childDN);
                        } else {
                            /*
							 * 鏁欏伐鎻掑叆
							 */
                        }
                    }
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }
}
