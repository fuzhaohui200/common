package com.ces.portal.common.shells.service.impl;

import com.ces.portal.common.shells.dao.ErrorShellScriptDao;
import com.ces.portal.common.shells.dao.impl.ErrorShellScriptDaoImpl;
import com.ces.portal.common.shells.pojo.ErrorShellScriptPojo;
import com.ces.portal.common.shells.service.ErrorShellScriptService;
import com.ces.portal.common.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorShellScriptServiceImpl implements ErrorShellScriptService {

    private Logger logger = LoggerFactory.getLogger(ErrorShellScriptServiceImpl.class);

    private ErrorShellScriptDao errorShellScriptDao = new ErrorShellScriptDaoImpl();

    @Override
    public void addErrorShellScript(ErrorShellScriptPojo errorShellScript) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            errorShellScriptDao.add(session, errorShellScript);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

}
