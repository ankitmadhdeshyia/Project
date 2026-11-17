package com.ecommerce.entity;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "orders_items")  
public class OrderItem {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name ="product_id") 
    private Product product;
    private int quantity;
    private double priceAtPurchase;
    private String orderStatus;
    private LocalDateTime orderAt;
    @ManyToOne
    @JoinColumn(name = "user_id")  
    private User customerName;
    public OrderItem() {
    }
    public OrderItem(Product product, int quantity, double priceAtPurchase, String orderStatus, LocalDateTime orderAt,
            User customerName) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.orderStatus = orderStatus;
        this.orderAt = orderAt;
        this.customerName = customerName;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }
    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public LocalDateTime getOrderAt() {
        return orderAt;
    }
    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }
    public User getCustomerName() {
        return customerName;
    }
    public void setCustomerName(User customerName) {
        this.customerName = customerName;
    }
   



}

