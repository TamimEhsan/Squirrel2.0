package com.tamimehsan.squirrel.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    public int wishListId;


    @Column(name = "user_id")
    private int userId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    public Book book;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "userId=" + userId +
                ", book=" + book.name +
                '}';
    }
}
