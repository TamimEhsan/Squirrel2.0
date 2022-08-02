package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.WishList;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface WishListRepository extends CrudRepository<WishList,Integer> {
    public ArrayList<WishList> getAllByUserId(int id);
}
