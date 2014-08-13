package com.ces.portal.common.shells.dao;

import com.ces.portal.common.shells.pojo.NonAgentResponseHistoryPojo;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public interface NonAgentResponseHistoryDao {

    public void saveOrUpdate(Session session, NonAgentResponseHistoryPojo nonAgentResponseHistory) throws HibernateException;

    public List<NonAgentResponseHistoryPojo> queryAll(Session session) throws HibernateException;

}
