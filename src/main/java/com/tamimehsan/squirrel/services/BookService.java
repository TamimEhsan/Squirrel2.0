package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.Picked;
import com.tamimehsan.squirrel.entity.Rating;
import com.tamimehsan.squirrel.repository.BookRepository;
import com.tamimehsan.squirrel.repository.PickedRepository;
import com.tamimehsan.squirrel.repository.RatingRepository;
import com.tamimehsan.squirrel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PickedRepository pickedRepository;

    public Book getBooksById(int id){
        return bookRepository.findBookByBookId(id);
    }

    public ArrayList<Book> getAllBook(){
        return bookRepository.findAll();
    }

    public long countAllBooks(){
        return bookRepository.count();
    }

    public ArrayList<Book> getAllReviewedBooks(int userId){
        return bookRepository.findAllByRatings_User_UserId(userId);
    }

    public ArrayList<Rating> getAllRatingsByUserId(int userId){
        return ratingRepository.getAllByUser_UserId(userId);
    }

    public ArrayList<Picked> getAllPickedBooks(int userId){
        return pickedRepository.findAllByCart_UserIdAndCart_State(userId,5);
    }

    public Rating getRatingById(int ratingId){
        return ratingRepository.findByRatingId(ratingId);
    }

    public Rating getRatingByUserIdAndBookId(int userId,int bookId){
        return ratingRepository.findFirstByUser_UserIdAndBook_BookId(userId,bookId);
    }


    public Picked getPickedByUserIdAndBookId(int userId,int bookId){
        return pickedRepository.findFirstByCart_UserIdAndBook_BookIdAndCart_State(userId,bookId,5);
    }

    public Rating updateRating(Rating rating){
        return ratingRepository.save(rating);
    }

    public Book updateBook(Book book){
        return bookRepository.save(book);
    }
}
