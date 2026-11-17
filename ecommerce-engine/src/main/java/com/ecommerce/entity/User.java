package com.ecommerce.entity;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "users")
@Table(name="users")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name="name", length=40 )
    private String name;
    private String email;
    private String phone;
    private boolean isActive=false;
    private LocalDateTime registerAt;
    private String password;
    @Column (name = "role", length = 20)
    private String role;
    @OneToMany(mappedBy = "customerName",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
   
    public User() {
    }
    public User(String name, String email, String phone, boolean isActive, LocalDateTime registerAt, String password,
            String role, List<OrderItem> orderItems) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.registerAt = registerAt;
        this.password = password;
        this.role = role;
        this.orderItems = orderItems;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public LocalDateTime getRegisterAt() {
        return registerAt;
    }
    public void setRegisterAt(LocalDateTime registerAt) {
        this.registerAt = registerAt;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    
    

}