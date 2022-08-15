package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.*;
import com.tamimehsan.squirrel.services.BookService;
import com.tamimehsan.squirrel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/my-section/reviews")
public class RatingController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;


    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private class returnObject{
        private String bookId;
        private String reviewId;
        private String review;
        private String rating;

        @Override
        public String toString() {
            return "returnObject{" +
                    "bookId='" + bookId + '\'' +
                    ", reviewId='" + reviewId + '\'' +
                    ", review='" + review + '\'' +
                    ", rating='" + rating + '\'' +
                    '}';
        }
    }

    @GetMapping("")
    public ModelAndView getAllBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        //ArrayList<WishList> wishes = wishListService.getAllByUser(user.getUserId());
        ArrayList<Rating> ratings = bookService.getAllRatingsByUserId(user.getUserId());
        System.out.println(ratings);
        ArrayList<Picked> picks = bookService.getAllPickedBooks(user.getUserId());
        System.out.println(picks);
        ArrayList<Book> unreviewedBooks = new ArrayList<>();
//        return new ModelAndView("redirect:/my-section/orders");
        HashMap<Integer,Boolean> isConsidered = new HashMap<>();
        HashMap<Integer,Boolean> isRated = new HashMap<>();
        for(Rating rating:ratings){
            isRated.put(rating.book.bookId, true);
        }
        for(Picked pick:picks){
            if(!isRated.containsKey(pick.book.bookId) && !isConsidered.containsKey(pick.book.bookId)){
                unreviewedBooks.add(pick.book);
                isConsidered.put(pick.book.bookId, true);
            }
        }
        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/myreviews");
        modelAndView.addObject("user",user);
        modelAndView.addObject("ratings",ratings);
        modelAndView.addObject("unreviewed",unreviewedBooks);
        modelAndView.addObject("navbar",-1);
        return modelAndView;
    }

    @GetMapping("/post")
    public ModelAndView postReview(@RequestParam(name="bookId",required = false,defaultValue = "0") String id1,
                                   @RequestParam(name="review",required = false,defaultValue = "") String review,
                                   @RequestParam(name="rating",required = false,defaultValue = "") String rate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id1);
        int rating = Integer.parseInt(rate);
        Book book = bookService.getBooksById(bookId);
        book.setStar(book.getStar() + rating);
        book.setReviewCount(book.getReviewCount() + 1);
        bookService.updateBook(book);
        Rating rating1 = new Rating();
        rating1.setBook(bookService.getBooksById(bookId));
        rating1.setUser(user);
        rating1.setStars(rating);
        rating1.setReview(review);
        bookService.updateRating(rating1);

        return new ModelAndView("redirect:/my-section/reviews");
    }

    // edit review
    @GetMapping( "/edit")
    public ModelAndView editReview(@RequestParam(name="bookId",required = false,defaultValue = "0") String id1,
                                   @RequestParam(name="reviewId",required = false,defaultValue = "0") String id2,
                                   @RequestParam(name="review",required = false,defaultValue = "") String review,
                                   @RequestParam(name="rating",required = false,defaultValue = "") String rate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id1);
        int reviewId = Integer.parseInt(id2);
        int stars = Integer.parseInt(rate);
        System.out.println(bookId);
        System.out.println(reviewId);
        System.out.println(review);
        System.out.println(stars);
        Rating rating = bookService.getRatingById(reviewId);
        Book book = bookService.getBooksById(bookId);
        book.setStar(book.getStar() + stars - rating.stars);
        bookService.updateBook(book);
        rating.setStars(stars);
        rating.setReview(review);
        bookService.updateRating(rating);
        return new ModelAndView("redirect:/my-section/reviews");

    }
}
