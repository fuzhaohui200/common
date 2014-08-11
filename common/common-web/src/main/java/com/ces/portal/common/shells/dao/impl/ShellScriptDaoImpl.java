package com.ces.portal.common.shells.dao.impl;

import com.ces.portal.common.shells.dao.ShellScriptDao;
import com.ces.portal.common.shells.pojo.ShellScriptPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class ShellScriptDaoImpl implements ShellScriptDao {

    @Override
    public void save(Session session, ShellScriptPojo shellScript) throws HibernateException {
        session.save(shellScript);
    }

    @Override
    public void update(Session session, ShellScriptPojo shellScript) throws HibernateException {
        session.update(shellScript);
    }

    @Override
    public void delete(Session session, ShellScriptPojo shellScript) throws HibernateException {
        session.delete(shellScript);
    }

    @Override
    public ShellScriptPojo queryShellScriptById(Session session, long shellScriptId) throws HibernateException {
        return (ShellScriptPojo) session.load(ShellScriptPojo.class, shellScriptId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShellScriptPojo> queryShellScriptsByGroupId(Session session, String groupId) throws HibernateException {
        String hql = "from ShellScriptPojo t where t.groupId =:groupId";
        return session.createQuery(hql).setParameter("groupId", groupId).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ShellScriptPojo> queryAllShellScriptsByFileName(Session session, String fileName, String groupId) {
        String hql = "from ShellScriptPojo t where t.groupId =:groupId and t.fileName =:fileName and t.lastExcuteTime is NULL order by t.sequence asc";
        return session.createQuery(hql).setParameter("groupId", groupId).setParameter("fileName", fileName).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> queryAllShellScriptFileNames(
            Session session) {
        String hql = "select distinct  t.fileName, t.groupId  from ShellScriptPojo t";
        List<Object[]> list = session.createQuery(hql).list();
        return list;
    }


}
