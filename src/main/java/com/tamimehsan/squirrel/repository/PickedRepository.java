package com.tamimehsan.squirrel.repository;


import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.entity.Picked;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PickedRepository extends CrudRepository<Picked,Integer> {

//    public Book getBookByBookId(int book_id);
    // find cart by cart id and book id
    public Picked findPickedByCart_CartIdAndBook_BookId(int cartId,int bookId);

    // find all picked book by user
    public ArrayList<Picked> findAllByCart_UserIdAndCart_State(int userId,int state);

    // find picked by user id and book id
    public Picked findFirstByCart_UserIdAndBook_BookIdAndCart_State(int userId,int bookId,int state);


}
