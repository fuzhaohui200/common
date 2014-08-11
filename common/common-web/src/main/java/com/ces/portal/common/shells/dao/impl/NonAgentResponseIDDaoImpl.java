package com.ces.portal.common.shells.dao.impl;

import com.ces.portal.common.shells.dao.NonAgentResponseIDDao;
import com.ces.portal.common.shells.pojo.NonAgentResponseIDPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class NonAgentResponseIDDaoImpl implements NonAgentResponseIDDao {

    @Override
    public void saveOrUpdate(Session session, NonAgentResponseIDPojo nonAgentResponseID) throws HibernateException {
        session.saveOrUpdate(nonAgentResponseID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NonAgentResponseIDPojo> queryAll(Session session) throws HibernateException {
        String hql = "from NonAgentResponseIDPojo t where t.status=0";
        return session.createQuery(hql).list();
    }

}
