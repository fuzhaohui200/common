package org.shine.common.ldap.exchange;

public interface IIMData2Mysql {

    /**
     * 将目录数据同步到Mysql数据库中
     *
     * @param passwordKey 密钥
     */
    public void synchonizeLdap2Mysql(String passwordKey);

    /**
     * 从即时通讯修改用户数据 修改本地数据的同时也修改门户数据
     *
     * @param cn          用户唯一用户名
     * @param passwordKey 密钥
     * @param password    修改的密码
     */
    public void fromIMModifyDataMysql(String cn, String passwordKey,
                                      String password);

}