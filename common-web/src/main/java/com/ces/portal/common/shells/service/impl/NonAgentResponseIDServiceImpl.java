package com.ces.portal.common.shells.service.impl;

import com.ces.portal.common.shells.dao.NonAgentResponseIDDao;
import com.ces.portal.common.shells.dao.impl.NonAgentResponseIDDaoImpl;
import com.ces.portal.common.shells.pojo.NonAgentResponseIDPojo;
import com.ces.portal.common.shells.service.NonAgentResponseIDService;
import com.ces.portal.common.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NonAgentResponseIDServiceImpl implements NonAgentResponseIDService {

    private Logger logger = LoggerFactory.getLogger(NonAgentResponseIDServiceImpl.class);
    private NonAgentResponseIDDao nonAgentResponseIDDao = new NonAgentResponseIDDaoImpl();

    @Override
    public void saveOrUpdate(NonAgentResponseIDPojo nonAgentResponseID) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            nonAgentResponseIDDao.saveOrUpdate(session, nonAgentResponseID);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }

    }

    @Override
    public List<NonAgentResponseIDPojo> queryAll() {
        Session session = HibernateUtil.currentSession();
        List<NonAgentResponseIDPojo> nonAgentResponseIDList = nonAgentResponseIDDao.queryAll(session);
        HibernateUtil.closeSession();
        return nonAgentResponseIDList;
    }

}
