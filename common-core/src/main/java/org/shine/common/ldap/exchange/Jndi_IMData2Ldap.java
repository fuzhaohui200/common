package org.shine.common.ldap.exchange;

import org.shine.common.ldap.utils.common.JNDI4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;

public class Jndi_IMData2Ldap {

    public static void main(String args[]) {
        new Jndi_IMData2Ldap().jndi_synchonizeLdap2Ldap();
    }

    /**
     * JNDI鏂瑰紡灏嗘暟鎹粠闂ㄦ埛LDAP鐩綍瀵煎叆鍒版湰鍦癓DAP鐩綍涓�
     */
    public void jndi_synchonizeLdap2Ldap() {

        JNDI4LdapUtils utils = new JNDI4LdapUtils();

		/* 闂ㄦ埛鏈嶅姟鍣↙DAP鐩綍鍦板潃 */
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://202.192.18.251:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Admin,o=gzhu,dc=cn");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, "gzhu_admin");
        env.put("com.sun.jndi.ldap.connect.pool", "true");

        // 鏌ヨ闂ㄦ埛LDAP鐩綍鏁版嵁
        NamingEnumeration<?> ne = utils.search(env, "o=gzhu,dc=cn", "cn=*");

		/* 鍗虫椂閫氳鏈嶅姟鍣↙DAP鐩綍鍦板潃 */
        Hashtable<String, Object> env1 = new Hashtable<String, Object>(11);
        env1.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env1.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env1.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env1.put(Context.SECURITY_AUTHENTICATION, "simple");
        env1.put(Context.SECURITY_CREDENTIALS, "123456");
        env1.put("com.sun.jndi.ldap.connect.pool", "true");

		/* 鍦ㄥ嵆鏃堕�璁湇鍔″櫒LDAP鐩綍涓坊鍔�缁�group */
        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute attr = new BasicAttribute("objectclass");
        attr.add("groupOfNames");
        attr.add("top");
        attrs.put(attr);

        int count = 0;
        try {
            // 瀹氫箟涓�釜Set鐢ㄤ簬鍒ゅ畾缁勫悕鐨勯噸澶�
            Set<String> set = new HashSet<String>();

			/* 灏嗘煡璇㈡暟鎹繘琛屽惊鐜� */
            while (ne.hasMore() && ne != null) {
                // 鍙栫粨鐐规暟鎹�
                SearchResult sr = (SearchResult) ne.next();
                // 鍙栫粨鐐规暟鎹殑鍛藉悕绌洪棿锛屼篃灏辨槸缁撶偣鐨凞N
                String name = sr.getNameInNamespace();
                // 鍙栫粨鐐圭殑鎵�湁灞炴�
                Attributes attrsVal = sr.getAttributes();
                // 灏嗙粨鐐圭殑DN鎴彇寰楀埌缁撶偣鐨刢n
                String cn = name.substring(name.indexOf("=") + 1,
                        name.indexOf(",")).trim();

                // 鍒ゆ柇缁撶偣鐨刢n鏄惁涓哄鐢�濡傛灉鏄鐢�鍒欒繘琛岀彮绾у垎缁�
                if (Pattern.matches("^[0-9]{10}$", cn)) {
                    // 鎴彇瀛︾敓鐨勭彮绾�
                    String className = cn.substring(0, 7);

                    // 缁欏垎缁勭粨鐐瑰姞鍏ユ爣璇嗗睘鎬u 灏嗙彮绾у彿鍔犲埌杩欎釜灞炴�閲�
                    attrs.put("ou", className);
                    // 鎷兼帴鐝骇缁撶偣鐨凞N
                    String dn = "ou=" + className + ",dc=test,dc=com";
                    // 鎷兼帴鐝骇瀛愮粨鐐�瀛︾敓缁撶偣DN 灏嗗鐢熺粨鐐瑰綊绾冲埌鐝骇閲�
                    String childDN = "cn=" + cn + ",ou=" + className
                            + ",dc=test,dc=com";

                    // 鍒ゆ柇鐝骇閲嶅 濡傛灉宸茬粡瀛樺湪浜�鍒欏彧闇�慨鏀圭彮绾ч噷member灞炴� 娌℃湁鍒欐坊鍔犵彮绾х粨鐐�
                    if (!set.contains(className)) {
                        // 鍚慡et闆嗗悎閲屾坊鍔犵彮绾у悕
                        set.add(className);
                        // 鍚戠彮绾х粨鐐规坊鍔犳垚鍛榤ember灞炴�
                        BasicAttribute attrMember = new BasicAttribute("member");
                        // 灏嗗瓙缁撶偣瀛︾敓DN鍔犲叆鍒版垚鍛榤ember閲�
                        attrMember.add(childDN);
                        // 鍚戠彮绾ц妭鐐规坊鍔犵粍鍛�
                        attrs.put(attrMember);
                        // 缁欑彮绾ф坊鍔犱竴涓猚n灞炴� 娉ㄦ剰:涓嶆坊鍔犱細鎶ラ敊
                        attrs.put("cn", className);
                        utils.add(env1, dn, attrs);
                    } else {
                        // 瀹氫箟涓�釜灞炴�闆�
                        Attributes addMember = new BasicAttributes();
                        // 瀹氫箟涓�釜member鐨勫熀鏈睘鎬�
                        BasicAttribute attrMember = new BasicAttribute("member");
                        // 鍚憁ember灞炴�閲屾坊鍔犲睘鎬у� 瀛愮粨鐐�鐨凞N
                        attrMember.add(childDN);
                        addMember.put(attrMember);
                        // 淇敼鐝骇缁撶偣 娣诲姞member灞炴�
                        utils.modify(env1, dn, DirContext.ADD_ATTRIBUTE,
                                addMember);
                    }

                    // 娣诲姞瀛︾敓缁撶偣
                    utils.add(env1, childDN, attrsVal);
                }

				/* 缁撶偣鐨刢n涓烘暀鑱屽憳宸� */
                else if (Pattern.matches("^[0-9]{6}$", cn)) {
                    // 鎴彇DN涓殑ou涓巓鐨勫睘鎬у�
                    System.out.println(name);
                    String ou_o = name.substring(name.indexOf("ou"),
                            name.lastIndexOf("o") - 1).trim();
                    // 瀹氫箟涓�釜瀛楃粨鐐圭殑DN
                    String childDN = "";

                    // 鍒ゆ柇鏄痮u鏄惁涓簎sers 濡傛灉涓嶆槸鍒欑洿鎺ュ懡鍚嶅悕缁勫悕ou
                    if (!ou_o.equals("ou=users")) {
                        // 鎴彇ou鍊�
                        String departmentName = ou_o.substring(3,
                                ou_o.lastIndexOf(","));
                        String dn = "ou=" + departmentName + ",dc=test,dc=com";
                        childDN = "cn=" + cn + ",ou=" + departmentName
                                + ",dc=test,dc=com";

                        // 鍒ゆ柇鏄惁宸茬粡娣诲姞杩囦簺閮ㄩ棬鍚�
                        if (!set.contains(departmentName)) {
                            set.add(departmentName);
                            // 鍚屾椂灏嗗憳宸ヤ俊鎭坊鍔犲埌閮ㄩ棬鎴愬憳灞炴�涓�
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            attrs.put(attrMember);
                            attrs.put("cn", departmentName);
                            attrs.put("ou", departmentName);
                            utils.add(env1, dn, attrs);
                        } else {
                            // 鍚戦儴闂ㄦ垚鍛樺睘鎬ч噷娣诲姞鏇村鐨勬垚鍛�
                            Attributes addMember = new BasicAttributes();
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            addMember.put(attrMember);
                            utils.modify(env1, dn, DirContext.ADD_ATTRIBUTE,
                                    addMember);
                        }
                    } else {
                        // 鑾峰彇閮ㄩ棬鍙�
                        String num_val = attrsVal.get("departmentNumber")
                                .toString();
                        String departmentNumber = num_val.substring(
                                num_val.indexOf(":") + 1, num_val.length())
                                .trim();
                        String dn = "ou=" + departmentNumber
                                + ",dc=test,dc=com";
                        childDN = "cn=" + cn + ",ou=" + departmentNumber
                                + ",dc=test,dc=com";
                        if (!set.contains(departmentNumber)) {
                            set.add(departmentNumber);
                            // 鍚屾椂灏嗗憳宸ヤ俊鎭坊鍔犲埌閮ㄩ棬鎴愬憳灞炴�涓�
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            attrs.put(attrMember);
                            attrs.put("cn", departmentNumber);
                            attrs.put("ou", departmentNumber);
                            utils.add(env1, dn, attrs);
                        } else {
                            Attributes addMember = new BasicAttributes();
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            addMember.put(attrMember);
                            utils.modify(env1, dn, DirContext.ADD_ATTRIBUTE,
                                    addMember);
                        }
                    }
                    utils.add(env1, childDN, attrsVal);
                }
                System.out.println("==========================" + ++count);
            }
        } catch (NamingException e) {
            // e.printStackTrace();
        }
    }

}
