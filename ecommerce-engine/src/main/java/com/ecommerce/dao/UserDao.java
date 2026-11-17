 package com.ecommerce.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecommerce.util.HibernateUtil;



public class UserDao {
    
     public void saveUser(com.ecommerce.entity.User user){
          Transaction tx=null;
          Session session=null;
          try{
            session=HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.persist(user);


          }catch(Exception e){
            if(tx!=null){
                tx.rollback();
            }
            System.out.println("database connection fails");
     }finally{
            if(session!=null){
               session.close();
            }
        }

    }
    
}