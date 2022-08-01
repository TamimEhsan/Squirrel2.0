package com.tamimehsan.squirrel.entity;

//book_id
//author_id
//publisher_id
//name
//image
//isbn
//page
//edition
//publishing_year
//price
//stock
//language
//genre
//summary
//star
//review_count

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    public int bookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    public Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    public Publisher publisher;

    @OneToMany(mappedBy = "book")
    public List<Rating> ratings;

    @Column
    public String name;

    @Column
    public String image;

    @Column
    public String isbn;

    @Column
    public int page;

    @Column
    public String edition;

    @Column(nullable = true)
    public Integer publishing_year;

    @Column
    public Integer price;

    @Column
    public Integer stock;

    @Column
    public String language;

    @Column
    public String genre;

    @Column
    public String summary;

    @Column
    public Integer star;

    @Column
    public Integer review_count;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Integer getPublishing_year() {
        return publishing_year;
    }

    public void setPublishing_year(Integer publishing_year) {
        this.publishing_year = publishing_year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getReview_count() {
        return review_count;
    }

    public void setReview_count(Integer review_count) {
        this.review_count = review_count;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                "\n, author=" + author.getName() +
                "\n, publisher=" + publisher.getName() +
                "\n, ratings=" + ratings +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", isbn='" + isbn + '\'' +
                ", page=" + page +
                ", edition='" + edition + '\'' +
                ", publishing_year=" + publishing_year +
                ", price=" + price +
                ", stock=" + stock +
                ", language='" + language + '\'' +
                ", genre='" + genre + '\'' +
                ", summary='" + summary + '\'' +
                ", star=" + star +
                ", review_count=" + review_count +
                '}';
    }
}
