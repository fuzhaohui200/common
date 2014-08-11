package org.shine.hibernate.test;

import org.hibernate.Session;

import java.util.List;

public class QueryTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Session session = HibernateUtil.currentSession();
        String hql = "from BlogPost t";
        List list = session.createQuery(hql).list();
        System.out.println(list);
    }

}
