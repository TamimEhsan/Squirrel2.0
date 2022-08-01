package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
}
