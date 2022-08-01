package com.tamimehsan.squirrel.entity;

//author_id
//name
//image
//decription

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    public int authorId;

    @Column
    public String name;

    @Column
    public String image;

    @Column
    public String description;

    @OneToMany(mappedBy = "author")
    public List<Book> books;


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int author_id) {
        this.authorId = author_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\n' +
                ", books=" + books +
                '}';
    }
}
