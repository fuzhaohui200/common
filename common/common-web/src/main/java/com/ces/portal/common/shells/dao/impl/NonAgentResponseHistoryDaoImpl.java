package com.ces.portal.common.shells.dao.impl;

import com.ces.portal.common.shells.dao.NonAgentResponseHistoryDao;
import com.ces.portal.common.shells.pojo.NonAgentResponseHistoryPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class NonAgentResponseHistoryDaoImpl implements
        NonAgentResponseHistoryDao {

    @Override
    public void saveOrUpdate(Session session, NonAgentResponseHistoryPojo nonAgentResponseHistory)
            throws HibernateException {
        session.saveOrUpdate(nonAgentResponseHistory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NonAgentResponseHistoryPojo> queryAll(Session session) throws HibernateException {
        // TODO Auto-generated method stub
        String hql = "from NonAgentResponseHistoryPojo t";
        return session.createQuery(hql).list();
    }

}
