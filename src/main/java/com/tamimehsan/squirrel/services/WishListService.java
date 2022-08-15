package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.entity.WishList;
import com.tamimehsan.squirrel.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private BookService bookService;

    public ArrayList<WishList> getAllByUser(int id){
        return wishListRepository.getAllByUserId(id);
    }

    public WishList findByUserAndBook(int userId,int bookId){
        return wishListRepository.findFirstByUserIdAndBook_BookId(userId, bookId);
    }

    public void save(WishList wishList){
        wishListRepository.save(wishList);
    }

    // addToWishList
    public void addToWishList(int userId,int bookId){
        WishList wishList = new WishList();
        wishList.setUserId(userId);
        wishList.setBook(bookService.getBooksById(bookId));
        wishListRepository.save(wishList);
    }
    // removeFromWishList
    public void removeFromWishList(int wishListId){
       // delete by wishlistid
        wishListRepository.deleteById(wishListId);
    }
}
