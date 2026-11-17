package com.ecommerce;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;


public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        User user=new User();
        user.setName("Ankit");
        user.setEmail("AnkitMadhdeshyia@gmail.com");
        user.setPhone("9090909090");
        user.setActive(true);
        user.setRegisterAt( LocalDateTime.now());
        user.setPassword("Ankit@123");
        user.setRole("Admin");

        Category ca=new Category();
        ca.setName("Electronics");
        ca.setDescription("This Is The Electronics Item");
        ca.setImageUrl("www.google.com");
        ca.setActive(true);
        ca.setCreatedAt(LocalDateTime.now());
        ca.setUpDateTime(LocalDateTime.now());

        Product p1=new Product();
        p1.setName("Mobile");
        p1.setBrand("Samsung");
        p1.setDescription("Samsung Mobile Is the best Phone ");
        p1.setDiscountPercentage("10%");
        p1.setSku("asdf123");
        p1.setPrice(12000);
        p1.setStock(10);

        
        Product p2=new Product();
        p2.setName("Mobile");
        p2.setBrand("Apple");
        p2.setDescription("Apple Mobile Is the best Phone ");
        p2.setDiscountPercentage("15%");
        p2.setSku("asdf1234");
        p2.setPrice(120000);
        p2.setStock(100);

       List<Product> list=new ArrayList<>();
       list.add(p1);
       list.add(p2);
       ca.setProducts(list);
  
       OrderItem oi=new OrderItem();

        
      
       


    }
}
