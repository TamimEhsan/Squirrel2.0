package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.entity.WishList;
import com.tamimehsan.squirrel.services.UserService;
import com.tamimehsan.squirrel.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/my-section/wishlist")
public class WishController {
    @Autowired
    private UserService userService;

    @Autowired
    WishListService wishListService;

    @GetMapping("")
    public ModelAndView getWish(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        ArrayList<WishList> wishes = wishListService.getAllByUser(user.getUserId());

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/wishlist");
        modelAndView.addObject("user",user);
        modelAndView.addObject("wishList",wishes);
        modelAndView.addObject("navbar",-1);
        return modelAndView;
    }
}
