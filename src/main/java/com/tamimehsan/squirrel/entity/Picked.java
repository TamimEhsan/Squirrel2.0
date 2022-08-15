package com.tamimehsan.squirrel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "picked")
public class Picked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picked_id")
    public int pickedId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    public Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    public Cart cart;

    public int amount;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    public Date createdAt;

    public int getPickedId() {
        return pickedId;
    }

    public void setPickedId(int pickedId) {
        this.pickedId = pickedId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Picked{" +
                "pickedId=" + pickedId +
                ", book=" + book +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
