package com.tamimehsan.squirrel.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    @OneToOne(mappedBy = "cart")
    public User user;

    public int getCartId() {
        return cartId;
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

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", createdAt='" + createdAt + '\'' +
                ", user=" + userId +
                '}';
    }
}
