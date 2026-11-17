package com.ecommerce.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static  SessionFactory sessionFactory;
    static {
        try{
            sessionFactory=new Configuration().configure("configuration.cfg.xml").buildSessionFactory();

        }catch(Exception e){
        System.out.println("Error on creating session factory");
        System.out.println("session factory Creation failed"+e.getMessage());
        }
    }
    public static SessionFactory getSessionFactory(){
        return  sessionFactory;
    }
    public static void shutDown(){
        if(sessionFactory!=null){
            sessionFactory.close();
        }
    }

}
