package com.tamimehsan.squirrel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

//publisher_id
//name
//image
@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    public int publisherId;

    @Column
    public String name;

    @Column
    public String image;

    @OneToMany(mappedBy = "publisher")
    @JsonManagedReference
    public List<Book> books;


    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisher_id) {
        this.publisherId = publisher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherId=" + publisherId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
