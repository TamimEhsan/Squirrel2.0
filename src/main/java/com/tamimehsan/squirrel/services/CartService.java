package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.entity.Picked;
import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.repository.BookRepository;
import com.tamimehsan.squirrel.repository.CartRepository;
import com.tamimehsan.squirrel.repository.PickedRepository;
import com.tamimehsan.squirrel.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PickedRepository pickedRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;


    // get all cart of a user
    public ArrayList<Cart> getAllCart(int userId){
        return cartRepository.findByUserId(userId);
    }
    public ArrayList<Cart> getAllCart(int userId,int state){
        return cartRepository.findByUserIdAndState(userId,state);
    }
    public Cart getOneCart(int cartId){
        return cartRepository.findCartByCartId(cartId);
    }


    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

    public void addItemToCart(Picked picked){
        pickedRepository.save(picked);
    }
    // add book to cart
    public void addItemToCart(int cartId,int bookId){
        Picked picked = new Picked();
        picked.setCart(cartRepository.findCartByCartId(cartId));
        picked.setBook(bookRepository.findBookByBookId(bookId));
        picked.setAmount(1);
        pickedRepository.save(picked);
    }
    public Picked getItemInCart(int cartId,int bookId){
        return pickedRepository.findPickedByCart_CartIdAndBook_BookId(cartId, bookId);
    }

    // update cart
    public void updateCart(int cartId,int bookId,int amount){
        Picked picked = pickedRepository.findPickedByCart_CartIdAndBook_BookId(cartId, bookId);
        picked.setAmount(amount);
        pickedRepository.save(picked);
    }
    // update cart from jsonarray
    public void updateCart(int cartId, JSONArray jsonArray){
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject book = jsonObject.getJSONObject("book");
            int bookId = book.getInt("bookId");
            int amount = jsonObject.getInt("amount");
            updateCart(cartId,bookId,amount);
        }
    }

    // delete item from cart
    public void deleteItemFromCart(int cartId,int bookId){
        Picked picked = pickedRepository.findPickedByCart_CartIdAndBook_BookId(cartId, bookId);
        pickedRepository.delete(picked);
    }

    // confirm cart
    public void confirmCart(int cartId,int userId){
        Cart cart = cartRepository.findCartByCartId(cartId);
        cart.setState(1);
        cartRepository.save(cart);
        assignNewCartToUser(userId);
    }
    //assign new cart to user
    public void assignNewCartToUser(int userId){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setState(0);
        cart = cartRepository.save(cart);
        User user = userRepository.findById(userId).get();
        user.setCart(cart);
        userRepository.save(user);
    }

    public ArrayList<Cart> getAllUncompleteOrder(){
        return cartRepository.findByStateGreaterThanEqualAndStateLessThanEqual(1,4);
    }
}
