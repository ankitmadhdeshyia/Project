 package com.ecommerce.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecommerce.util.HibernateUtil;



public class OrderItemDao {
    
     public void saveOrderItem(com.ecommerce.entity.OrderItem orderItems){
          Transaction tx=null;
          Session session=null;
          try{
            session=HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.persist(orderItems);


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