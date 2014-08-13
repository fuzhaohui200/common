package com.ces.portal.common.shells.dao;

import com.ces.portal.common.shells.pojo.ShellScriptPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public interface ShellScriptDao {

    public void save(Session session, ShellScriptPojo shellScript) throws HibernateException;

    public void update(Session session, ShellScriptPojo shellScript) throws HibernateException;

    public void delete(Session session, ShellScriptPojo shellScript) throws HibernateException;

    public ShellScriptPojo queryShellScriptById(Session session, long shellScriptId) throws HibernateException;

    public List<ShellScriptPojo> queryShellScriptsByGroupId(Session session, String groupId) throws HibernateException;

    public List<ShellScriptPojo> queryAllShellScriptsByFileName(Session session, String fileName, String groupId);

    public List<Object[]> queryAllShellScriptFileNames(Session session);

}
