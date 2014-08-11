/**
 * Copyright (c) 2008-2011 Guangzhou LianYi Information Technology Co,.Ltd. All rights reserved.  
 * lyasp_v3.0 2012-9-6
 *
 * 相关描述： 
 *
 */
package com.ces.portal.common.utils;


import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ces.portal.common.shells.thread.ShellsScriptThreadListener;

@SuppressWarnings("deprecation")
public class HibernateUtil {

    public static final ThreadLocal<Session> MAP = new ThreadLocal<Session>();
    private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory SESSION_FACTORY = null;

    private HibernateUtil() {
    }

    static {
        try {
          if(ShellsScriptThreadListener.HIBERANTE_CONFG  != null && !ShellsScriptThreadListener.HIBERANTE_CONFG.equals("")) {
                File hibernateXml = new File(ShellsScriptThreadListener.HIBERANTE_CONFG );
            	SESSION_FACTORY = new Configuration().configure(hibernateXml).buildSessionFactory();
            } else {
            	SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
            }
            logger.debug("HibernateUtil.static - end");
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public static Session currentSession() throws HibernateException {
        Session s = (Session) MAP.get();
        if (s == null) {
            s = SESSION_FACTORY.openSession();
            MAP.set(s);
        }
        return s;
    }

    /**
     * Closes the Hibernate Session.  Users must call this method after calling
     * {@link #currentSession() currentSession()}.
     *
     * @throws HibernateException if session has problem closing.
     */
    public static void closeSession() throws HibernateException {
        Session s = (Session) MAP.get();
        MAP.set(null);
        if (s != null) {
            s.close();
        }
    }

}
