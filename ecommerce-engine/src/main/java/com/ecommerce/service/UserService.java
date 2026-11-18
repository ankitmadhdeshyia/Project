package com.ecommerce.service;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;

public class UserService{
    public void addProduct(User vendor, Product product){
      if("Vendor".equalsIgnoreCase(vendor.getRole())){
        ProductDao productdao0=new ProductDao();
        productdao0.saveProduct(product);
      }else{
        System.out.println("Access Denied Only vendor cam add product");
      }
    }
    
}