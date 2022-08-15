package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RatingRepository extends CrudRepository<Rating, Integer> {
    public Rating findFirstByUser_UserIdAndBook_BookId(int userId, int bookId);
    public void deleteByUser_UserIdAndBook_BookId(int userId,int bookId);
    // get all ratings by user id
    public ArrayList<Rating> getAllByUser_UserId(int id);

    // get rating by rating id
    public Rating findByRatingId(int id);
}

