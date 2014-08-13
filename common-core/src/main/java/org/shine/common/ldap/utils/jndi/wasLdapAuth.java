package org.shine.common.ldap.utils.jndi;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//include the JNDI in the classpath. You should use the same JDK used by WebSphere Application server.
public class wasLdapAuth {

    public String ldapHost = "ldap://202.192.18.251:389";
    // ldap host +
    // port number
    // ldap鏈嶅姟鍣ㄧ殑涓绘満IP浠ュ強绔彛鍙�

    public String DN = "cn=Admin,o=gzhu,dc=cn";
    // DN to be
    // authenticated
    // 鍩虹DN

    public String password = "gzhu_admin";

    // DN's password
    // DN瀵嗙爜

    public wasLdapAuth() {

    }

    // ***************** End of user information
    public static void main(String[] args) {

        wasLdapAuth wLdapAuth = new wasLdapAuth();

        // 1.鏌ヨ鑺傜偣
        wLdapAuth.search(wLdapAuth.getProperties());

        // 2.娣诲姞鑺傜偣
        // wLdapAuth.addNode(wLdapAuth.getProperties());

        // wLdapAuth.addNode1(wLdapAuth.getProperties());

        // 3.淇敼鑺傜偣
        // wLdapAuth.modifyNode(wLdapAuth.getProperties());

        // 4.鍒犻櫎鑺傜偣
        // wLdapAuth.deleteNode(wLdapAuth.getProperties());
    }

    // 寰楀埌灞炴�
    public Properties getProperties() {

        Properties props = new Properties();
        // 灞炴�绫伙紝鍙互鎶婂睘鎬у～鍏roperties

        props.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        // INITIAL_CONTEXT_FACTORY

        // for websphere 4.0 and 5.0
        // props.put(Context.INITIAL_CONTEXT_FACTORY,
        // "com.ibm.jndi.LDAPCtxFactory");
        // for WebSphere 3.5 release

        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        // 绠�崟璁よ瘉 // authentication

        props.put(Context.SECURITY_CREDENTIALS, password);
        // 瀵嗙爜

        props.put(Context.SECURITY_PRINCIPAL, DN);
        // 鍩虹DN

        props.put(Context.PROVIDER_URL, ldapHost);
        // IP鍙婄鍙ｅ彿

        return props;

    }

    // 鏌ヨ鑺傜偣
    public void search(Properties props) {
        // 璁＄畻鎵�敤鏃堕棿
        long start = System.currentTimeMillis();
        long end = 0;
        long time = 0;

        try {
            System.out.println("....璁よ瘉涓�....");

            // 鏂囦欢鐩綍涓婁笅鏂�
            DirContext ctx = new InitialDirContext(props);

            // 璁剧疆鏌ヨ鑼冨洿骞跺紑濮嬫煡璇�
            SearchControls constraints = new SearchControls();

            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // subtree_scope

            String root = "o=gzhu,dc=cn";
            // 琛ㄧず鏌ヨ鐨勬牴

            String filter = "cn=*"; //
            // 琛ㄧず鏌ヨ鏉′欢,*琛ㄧず閫氶厤绗︼紝姝ｅ垯琛ㄨ揪寮忎腑浣跨敤

            // 鏌ヨ澶氫釜缁撴灉,搴旇鍋氭垚娉涘瀷妯℃澘
            // 浣跨敤Enumeration
            NamingEnumeration results = ctx.search(root, filter, constraints);

            // 鐢虫槑涓や釜瀛楃涓�
            String temp = new String();

            // 鍙彉瀛楃涓�
            StringBuffer res = new StringBuffer();

            // 鏌ヨ鑺傜偣
            int count = 0;
            // 鎵撳嵃鏌ヨ缁撴灉,杩涜閬嶅巻
            while (results != null && results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                // 寰楀埌鐢ㄦ埛鍚�
                String dn = sr.getName();
                System.out
                        .println("=================================================");
                System.out.println("鍚嶅瓧涓� \t" + dn);
                System.out.println(sr.getAttributes());
                Attributes ab = sr.getAttributes();
                NamingEnumeration values = ((BasicAttribute) ab
                        .get("givenname")).getAll();
                int num = 0;
                System.out.println(count++
                        + "-----------------------------------------------");
                while (values.hasMore()) {
                    // 褰撲笉涓虹┖鐨勬椂鍊�
                    if (res.length() > 0) {
                        res.append("|");
                    }
                    res.append(values.next().toString());
                }
            }

            // 浠ュ瓧绗︿覆褰㈠紡鎵撳嵃
            System.out.println("濮�\t" + res.toString());

            // 浠ラ泦鍚堟柟寮忔墦鍗�
            // for(int i=0;i<aList.size();i++){
            // System.out.println(aList.get(i));
            // }
            //
            // temp=res.toString();

            // String[] arr_givename=temp.split("|");
            //
            // for (int i = 0; i < arr_givename.length; i++) {
            // System.out.println(i+":\t"+arr_givename[i]);
            // }
            //
            //

            // 瑕佸嵆鏃跺叧闂�
            // if (dc != null) {
            // try {
            // dc.close();
            // } catch (NamingException e) {
            // }
            // }

            end = System.currentTimeMillis();
            time = end - start;

            System.out.println();

            System.out.println("璁よ瘉鎵�彂鏃堕棿 " + time + " millis");
            System.out.println("鎴愬姛璁よ瘉DN: " + DN);

        } catch (Exception ex) {
            end = System.currentTimeMillis();
            time = end - start;
            System.out.println("Exception is " + ex.toString());
            ex.printStackTrace();
            System.out.println("authentication takes = " + time + " millis");
            System.out.println("fail to authenticate DN: " + DN);
        }

    }

    // 娣诲姞鑺傜偣
    public void addNode(Properties props) {

        // 鏂囦欢鐩綍涓婁笅鏂�
        DirContext ctx = null;

        try {
            ctx = new InitialDirContext(props);

            String newUserName = "501745";
            BasicAttributes attrsbu = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectclass");
            objclassSet.add("organizationalUnit");
            objclassSet.add("top");

            attrsbu.put(objclassSet);

            attrsbu.put("cn", newUserName);
            // attrsbu.put("ou", "Admin");
            attrsbu.put("sn", "鍘�");

            // attrsbu.put("sn", newUserName);
            // attrsbu.put("uid", newUserName);

            String DNN = "cn=" + newUserName + ","
                    + "ou=Admin,o=account,o=gzhu,dc=cn";
            Map map = new HashMap();
            map.put("cn", newUserName);
            map.put("sn", "鍘�");

            map.put("objectclass", objclassSet);

            // ctx.createSubcontext(DNN, attrsbu);
            ctx.bind(DNN, map);

            // 娣诲姞瀛愯妭鐐�

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 娣诲姞鑺傜偣
    public void addNode1(Properties props) {

        // 鏂囦欢鐩綍涓婁笅鏂�
        DirContext ctx = null;

        try {
            ctx = new InitialDirContext(props);

            String newUserName = "501748";
            BasicAttributes attrsbu = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectclass");
            objclassSet.add("organizationalPerson");
            objclassSet.add("top");
            objclassSet.add("inetOrgPerson");
            objclassSet.add("person");

            attrsbu.put(objclassSet);

            attrsbu.put("cn", newUserName);
            attrsbu.put("sn", "112");

            // attrsbu.put("sn", newUserName);
            // attrsbu.put("uid", newUserName);

            String DNN = "cn=" + newUserName + ","
                    + "ou=Admin,o=account,o=gzhu,dc=cn";

            ctx.createSubcontext(DNN, attrsbu);// .bind(DNN, attrsbu);

            System.out.println("鎴愬姛娣诲姞鑺傜偣" + DNN);

            // 娣诲姞瀛愯妭鐐�

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 淇敼鑺傜偣
    public void modifyNode(Properties props) {

        // 鏂囦欢鐩綍涓婁笅鏂�
        DirContext ctx = null;

        try {
            ctx = new InitialDirContext(props);

            String newUserName = "501747";

            // Attributes attrsbu = new BasicAttributes();
            // BasicAttribute objclassSet = new BasicAttribute("objectclass");
            // objclassSet.add("organizationalPerson");
            // objclassSet.add("top");
            // objclassSet.add("inetOrgPerson");
            // objclassSet.add("person");
            //
            // attrsbu.put(objclassSet);
            //
            //
            // attrsbu.put("cn", newUserName);
            // attrsbu.put("sn", "鏄�);
            //
            // //attrsbu.put("sn", newUserName);
            // //attrsbu.put("uid", newUserName);

            String DNN = "cn=" + newUserName + ","
                    + "ou=Admin,o=account,o=gzhu,dc=cn";

            Attribute attribute = new BasicAttribute("sn", "鏄庢槑");
            // ctx.createSubcontext(DNN, attrsbu);//.bind(DNN, attrsbu);
            ModificationItem mItem = new ModificationItem(
                    DirContext.REPLACE_ATTRIBUTE, attribute);

            ModificationItem[] mItems = new ModificationItem[]{mItem};

            ctx.modifyAttributes(DNN, mItems);

            System.out.println("鎴愬姛淇敼鑺傜偣" + DNN);

            // 娣诲姞瀛愯妭鐐�

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void searchNode() {

    }

    // ***************** user information to be authenticated
    // ********************************
    // *****************Please modify the following three properties
    // accordingly ************

    // 鍒犻櫎鑺傜偣
    public void deleteNode(Properties props) {

        // 鏂囦欢鐩綍涓婁笅鏂�
        DirContext ctx = null;

        try {
            ctx = new InitialDirContext(props);

            String newUserName = "501747";

            String DNN = "cn=" + newUserName + ","
                    + "ou=Admin,o=account,o=gzhu,dc=cn";

            ctx.unbind(DNN);
            ctx.destroySubcontext(DNN);

            System.out.println("鎴愬姛鍒犻櫎鑺傜偣" + DNN);

            // 娣诲姞瀛愯妭鐐�

        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
