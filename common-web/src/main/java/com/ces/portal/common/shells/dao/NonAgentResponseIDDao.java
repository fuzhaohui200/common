package com.ces.portal.common.shells.dao;

import com.ces.portal.common.shells.pojo.NonAgentResponseIDPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public interface NonAgentResponseIDDao {

    public void saveOrUpdate(Session session, NonAgentResponseIDPojo nonAgentResponseID) throws HibernateException;

    public List<NonAgentResponseIDPojo> queryAll(Session session) throws HibernateException;

}
