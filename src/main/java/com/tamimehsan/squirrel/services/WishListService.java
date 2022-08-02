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

    public ArrayList<WishList> getAllByUser(int id){
        return wishListRepository.getAllByUserId(id);
    }


}
