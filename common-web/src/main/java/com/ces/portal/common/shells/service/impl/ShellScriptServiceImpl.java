package com.ces.portal.common.shells.service.impl;

import com.ces.portal.common.shells.dao.ShellScriptDao;
import com.ces.portal.common.shells.dao.impl.ShellScriptDaoImpl;
import com.ces.portal.common.shells.pojo.ShellScriptPojo;
import com.ces.portal.common.shells.service.ShellScriptService;
import com.ces.portal.common.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShellScriptServiceImpl implements ShellScriptService {

    private Logger logger = LoggerFactory.getLogger(ShellScriptServiceImpl.class);
    private ShellScriptDao shellScriptDao = new ShellScriptDaoImpl();

    @Override
    public void add(ShellScriptPojo shellScriptPojo) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            shellScriptDao.save(session, shellScriptPojo);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void update(ShellScriptPojo shellScriptPojo) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            shellScriptDao.update(session, shellScriptPojo);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void delete(long shellScriptId) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.currentSession();
            transaction = session.beginTransaction();
            ShellScriptPojo shellScriptPojo = this.queryShellScriptPojoById(shellScriptId);
            shellScriptDao.delete(session, shellScriptPojo);
            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            transaction.rollback();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public ShellScriptPojo queryShellScriptPojoById(long shellScriptId) {
        ShellScriptPojo shellScriptPojo = null;
        try {
            Session session = HibernateUtil.currentSession();
            shellScriptPojo = shellScriptDao.queryShellScriptById(session, shellScriptId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return shellScriptPojo;
    }

    @Override
    public List<ShellScriptPojo> queryShellScriptPojosByGroupId(String groupId) {
        List<ShellScriptPojo> shellScriptPojos = new ArrayList<ShellScriptPojo>();
        try {
            Session session = HibernateUtil.currentSession();
            shellScriptPojos = shellScriptDao.queryShellScriptsByGroupId(session, groupId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return shellScriptPojos;
    }

    @Override
    public List<ShellScriptPojo> queryAllShellScriptsByFileName(String fileName, String groupId) {
        // TODO Auto-generated method stub
        List<ShellScriptPojo> shellScriptPojos = new ArrayList<ShellScriptPojo>();
        try {
            Session session = HibernateUtil.currentSession();
            shellScriptPojos = shellScriptDao.queryAllShellScriptsByFileName(session, fileName.trim(), groupId.trim());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return shellScriptPojos;
    }

    @Override
    public List<Object[]> queryAllShellScriptFileNames() {
        // TODO Auto-generated method stub
        List<Object[]> shellScriptFileNameList = new ArrayList<Object[]>();
        try {
            Session session = HibernateUtil.currentSession();
            shellScriptFileNameList = shellScriptDao.queryAllShellScriptFileNames(session);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            HibernateUtil.closeSession();
        }
        return shellScriptFileNameList;
    }
}
