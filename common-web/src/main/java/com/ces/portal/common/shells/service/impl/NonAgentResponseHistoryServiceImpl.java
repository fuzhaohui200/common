package com.ces.portal.common.shells.service.impl;

import com.ces.portal.common.shells.dao.NonAgentResponseHistoryDao;
import com.ces.portal.common.shells.dao.impl.NonAgentResponseHistoryDaoImpl;
import com.ces.portal.common.shells.pojo.NonAgentResponseHistoryPojo;
import com.ces.portal.common.shells.service.NonAgentResponseHistoryService;
import com.ces.portal.common.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NonAgentResponseHistoryServiceImpl implements
        NonAgentResponseHistoryService {

    private Logger logger = LoggerFactory.getLogger(NonAgentResponseHistoryServiceImpl.class);

    private NonAgentResponseHistoryDao nonAgentResponseHistoryDao = new NonAgentResponseHistoryDaoImpl();

    @Override
    public void saveOrUpdate(NonAgentResponseHistoryPojo nonAgentResponseHistory) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            nonAgentResponseHistoryDao.saveOrUpdate(session, nonAgentResponseHistory);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public List<NonAgentResponseHistoryPojo> queryAll() {
        Session session = HibernateUtil.currentSession();
        List<NonAgentResponseHistoryPojo> nonAgentResponseHistoryList = nonAgentResponseHistoryDao.queryAll(session);
        HibernateUtil.closeSession();
        return nonAgentResponseHistoryList;
    }

}
