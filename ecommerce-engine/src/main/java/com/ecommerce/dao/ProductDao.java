 package com.ecommerce.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecommerce.util.HibernateUtil;



public class ProductDao {
    
     public void saveProduct(com.ecommerce.entity.Product product){
          Transaction tx=null;
          Session session=null;
          try{
            session=HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.persist(product);


          }catch(Exception e){
            if(tx!=null){
                tx.rollback();
            }
            System.out.println("database connection failes");
     }finally{
            if(session!=null){
               session.close();
            }
        }

    }
    
}