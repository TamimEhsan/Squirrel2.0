package com.tamimehsan.squirrel.entity;

import javax.persistence.*;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
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

    @Override
    public String toString() {
        return "WishList{" +
                "userId=" + userId +
                ", book=" + book.name +
                '}';
    }
}
