package org.shine.hibernate.test;

import org.hibernate.Session;
import org.shine.hibernate.test.entity.Person;

public class Test {

    public static void main(String[] args) {
        Session sess = HibernateSessionFactory.getSession();
        sess.beginTransaction();
        sess.save(new Person("123", "123"));
        sess.getTransaction().commit();
        System.out.println(sess);
    }

}
