package org.shine.common.ldap.utils.jndi;

import org.junit.Test;
import org.shine.common.ldap.utils.common.JNDI4LdapUtils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;

public class LDAP_251TO33_TeamTest {
    /**
     * 鍒嗙粍
     */
    @Test
    public void addClassStudent() {

        Hashtable<String, Object> env1 = new Hashtable<String, Object>(11);
        env1.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env1.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env1.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env1.put(Context.SECURITY_AUTHENTICATION, "simple");
        env1.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env1.put("com.sun.jndi.ldap.connect.pool", "true");

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute attr = new BasicAttribute("objectclass");
        attr.add("organizationalUnit");
        attr.add("top");
        attrs.put(attr);
        attrs.put("ou", "students");

        String dn = "ou=students,dc=test,dc=com";
        new JNDI4LdapUtils().add(env1, dn, attrs);

        BasicAttributes attrs1 = new BasicAttributes();
        BasicAttribute attr1 = new BasicAttribute("objectclass");
        attr1.add("inetOrgPerson");
        attr1.add("organizationalPerson");
        attr1.add("person");
        attr1.add("top");
        attrs1.put(attr1);
        attrs1.put("cn", "888888");
        attrs1.put("sn", "aaa");
        String DNN = "cn=888888,ou=students,dc=test,dc=com";
        new JNDI4LdapUtils().add(env1, DNN, attrs1);
    }

    @Test
    public void testCopyData() {
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

        Hashtable<String, Object> env2 = new Hashtable<String, Object>(11);
        env2.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env2.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env2.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env2.put(Context.SECURITY_AUTHENTICATION, "simple");
        env2.put(Context.SECURITY_CREDENTIALS, "123456");

        // Enable connection pooling
        env2.put("com.sun.jndi.ldap.connect.pool", "true");
        int count = 0;
        try {
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String name = sr.getName();
                String cn = name.substring(0, name.indexOf(",")).trim();
                System.out.println(cn);
                Attributes attrs = sr.getAttributes();
                // NamingEnumeration params = attrs.getAll();
                // BasicAttributes attrsbu = new BasicAttributes();
                // while(params.hasMore()){
                // attrsbu.put(params.next());
                // }
                String dn = cn + ",ou=users,dc=test,dc=com";
                new JNDI4LdapUtils().add(env2, dn, attrs);
                count++;
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(count);
        // NamingEnumeration ne = new LdapUtils().search(base, filter);
    }

    /**
     * 鍒嗙粍
     */
    @Test
    public void addClassStudent1() {
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

        Hashtable<String, Object> env1 = new Hashtable<String, Object>(11);
        env1.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env1.put(Context.PROVIDER_URL, "ldap://202.192.21.33:389");
        env1.put(Context.SECURITY_PRINCIPAL, "cn=root,dc=test,dc=com");
        env1.put(Context.SECURITY_AUTHENTICATION, "simple");
        env1.put(Context.SECURITY_CREDENTIALS, "123456");
        // Enable connection pooling
        env1.put("com.sun.jndi.ldap.connect.pool", "true");

        BasicAttributes attrs = new BasicAttributes();
        BasicAttribute attr = new BasicAttribute("objectclass");
        attr.add("organizationalUnit");
        attr.add("top");
        attrs.put(attr);

        int count = 0;
        try {
            // String staff_dn = "ou=staff,dc=test,dc=com";
            // new LdapUtils().add(env1, staff_dn, attrs);
            Set set = new HashSet();
            while (ne.hasMore() && ne != null) {
                SearchResult sr = (SearchResult) ne.next();
                String name = sr.getName();
                Attributes attrsVal = sr.getAttributes();

                String cn = name.substring(name.indexOf("=") + 1,
                        name.indexOf(",")).trim();
                if (Pattern.matches("^[0-9]{10}$", cn)) {
                    String className = cn.substring(0, 7);
                    attrs.put("ou", className);
                    String dn = "ou=" + className + ",dc=test,dc=com";
                    if (!set.contains(className)) {
                        System.out.println("=========================="
                                + ++count);
                        set.add(className);
                        BasicAttribute attrMember = new BasicAttribute("member");
                        attrMember.add(cn);
                        attrs.put(attrMember);
                        new JNDI4LdapUtils().add(env1, dn, attrs);
                    } else {
                        Attributes addMember = new BasicAttributes();
                        BasicAttribute attrMember = new BasicAttribute("member");
                        attrMember.add(cn);
                        new JNDI4LdapUtils().modify(env1, dn,
                                DirContext.ADD_ATTRIBUTE, addMember);
                    }
                    String dn1 = "cn=" + cn + ",ou=" + className
                            + ",dc=test,dc=com";
                    new JNDI4LdapUtils().add(env1, dn1, attrsVal);
                }
                // else{
                // String dn1="cn="+cn+",ou=staff,dc=test,dc=com";
                // new LdapUtils().add(env1, dn1, attrsVal);
                // }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 浠�51鍙栨暟鎹�鍦�3娴嬭瘯鏈嶅姟鍣ㄤ笂杩涜groupOfNames鍒嗙粍
     */
    @Test
    public void addClassStudent2() {

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
        NamingEnumeration ne = new JNDI4LdapUtils().search(env, "o=gzhu,dc=cn",
                "cn=*");

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
        int staff = 0;
        try {
            // 瀹氫箟涓�釜Set鐢ㄤ簬鍒ゅ畾缁勫悕鐨勯噸澶�
            Set set = new HashSet();

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
                        new JNDI4LdapUtils().add(env1, dn, attrs);
                    } else {
                        // 瀹氫箟涓�釜灞炴�闆�
                        Attributes addMember = new BasicAttributes();
                        // 瀹氫箟涓�釜member鐨勫熀鏈睘鎬�
                        BasicAttribute attrMember = new BasicAttribute("member");
                        // 鍚憁ember灞炴�閲屾坊鍔犲睘鎬у� 瀛愮粨鐐�鐨凞N
                        attrMember.add(childDN);
                        addMember.put(attrMember);
                        // 淇敼鐝骇缁撶偣 娣诲姞member灞炴�
                        new JNDI4LdapUtils().modify(env1, dn,
                                DirContext.ADD_ATTRIBUTE, addMember);
                    }

                    // 娣诲姞瀛︾敓缁撶偣
                    new JNDI4LdapUtils().add(env1, childDN, attrsVal);
                }

				/* 缁撶偣鐨刢n涓烘暀鑱屽憳宸� */
                else if (Pattern.matches("^[0-9]{6}$", cn)) {
                    // 鎴彇DN涓殑ou涓巓鐨勫睘鎬у�
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
                            new JNDI4LdapUtils().add(env1, dn, attrs);
                        } else {
                            // 鍚戦儴闂ㄦ垚鍛樺睘鎬ч噷娣诲姞鏇村鐨勬垚鍛�
                            Attributes addMember = new BasicAttributes();
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            addMember.put(attrMember);
                            new JNDI4LdapUtils().modify(env1, dn,
                                    DirContext.ADD_ATTRIBUTE, addMember);
                        }
                    } else {
                        // 鑾峰彇閮ㄩ棬鍙�
                        String num_val = attrsVal.get("departmentNumber")
                                .toString();
                        String departmentNumber = num_val.substring(
                                num_val.indexOf(":") + 1, num_val.length())
                                .trim();
                        // 鑾峰彇閮ㄩ棬鍚嶇О
                        String name_val = attrsVal.get("displayName")
                                .toString();
                        String departmentName = name_val.substring(
                                name_val.indexOf(":") + 1, name_val.length())
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
                            new JNDI4LdapUtils().add(env1, dn, attrs);
                        } else {
                            Attributes addMember = new BasicAttributes();
                            BasicAttribute attrMember = new BasicAttribute(
                                    "member");
                            attrMember.add(childDN);
                            addMember.put(attrMember);
                            new JNDI4LdapUtils().modify(env1, dn,
                                    DirContext.ADD_ATTRIBUTE, addMember);
                        }
                    }
                    new JNDI4LdapUtils().add(env1, childDN, attrsVal);
                }
                System.out.println("==========================" + ++count);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTime() {
        System.out.println("00" + new Date().getTime());

        try {
            System.out.println(new String("符朝辉".getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
