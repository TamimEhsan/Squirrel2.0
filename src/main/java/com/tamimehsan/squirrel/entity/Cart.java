package com.tamimehsan.squirrel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    public int cartId;

    @Column(name = "user_id")
    public int userId;
    @Column(name = "state")
    public int state;

    @OneToMany(mappedBy = "cart")
    public List<Picked> pickeds;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    @OneToOne(mappedBy = "cart")
    @JsonBackReference
    public User user;

    @Column(name = "total_price")
    public int totalPrice;

    @Column(name = "total_item")
    public int totalItem;

    public int pick;

    public String address;

    public String phone1;

    public String phone2;

    @Column(name = "payment_method")
    public String paymentMethod;

    public String name;

    public int getCartId() {
        return cartId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Picked> getPickeds() {
        return pickeds;
    }

    public void setPickeds(List<Picked> pickeds) {
        this.pickeds = pickeds;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", state=" + state +
                ", createdAt=" + createdAt +
                ", totalPrice=" + totalPrice +
                ", totalItem=" + totalItem +
                ", pick=" + pick +
                ", address='" + address + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
