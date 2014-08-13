package com.ces.portal.common.shells.dao;

import com.ces.portal.common.shells.pojo.ErrorShellScriptPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface ErrorShellScriptDao {

    public void add(Session session, ErrorShellScriptPojo errorShellScriptPojo) throws HibernateException;

}
