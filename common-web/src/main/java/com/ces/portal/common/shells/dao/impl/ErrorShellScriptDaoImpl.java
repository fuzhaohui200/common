package com.ces.portal.common.shells.dao.impl;

import com.ces.portal.common.shells.dao.ErrorShellScriptDao;
import com.ces.portal.common.shells.pojo.ErrorShellScriptPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ErrorShellScriptDaoImpl implements ErrorShellScriptDao {

    public void add(Session session, ErrorShellScriptPojo errorShellScriptPojo) throws HibernateException {
        session.saveOrUpdate(errorShellScriptPojo);
    }
}
