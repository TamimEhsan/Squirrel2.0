package com.tamimehsan.squirrel.repository;


import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CartRepository extends CrudRepository<Cart,Integer> {

//    public Book getBookByBookId(int book_id);


    // find cart by cart id
    public Cart findCartByCartId(int cartId);

    // find cart by user id
    public ArrayList<Cart> findByUserId(int userId);
    public ArrayList<Cart> findByUserIdAndState(int userId,int state);

    // find all cart with state greater than 1 and less than 5
    public ArrayList<Cart> findByStateGreaterThanEqualAndStateLessThanEqual(int state1,int state2);



}
