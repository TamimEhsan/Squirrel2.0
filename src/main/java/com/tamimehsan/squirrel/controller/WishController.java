package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.Picked;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/add/{id}")
    public RedirectView addBookToWishList(@PathVariable String id, RedirectAttributes attributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id);
        System.out.println("bookId::"+bookId);
        WishList wishList = wishListService.findByUserAndBook(user.getUserId(),bookId);
        if (wishList == null ){
            // add to wishlist
            wishListService.addToWishList(user.getUserId(),bookId);
        }else{
            attributes.addFlashAttribute("errorAttribute", "Book Already Added in wishList");
            return new RedirectView("/books/"+id);
        }
        System.out.println("wishlist added");
        attributes.addFlashAttribute("successAttribute", "Book Successfully Added in wishlist");
        return new RedirectView("/books/"+id);
    }
    @GetMapping("/remove/{id}")
    public RedirectView removeBookFromWishList(@PathVariable String id, RedirectAttributes attributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id);
        System.out.println("bookId::"+bookId);
        WishList wishList = wishListService.findByUserAndBook(user.getUserId(),bookId);
        if (wishList != null ){
            // remove from wishlist
            wishListService.removeFromWishList(wishList.getWishListId());
        }else{
            attributes.addFlashAttribute("errorAttribute", "Book Not Found in wishList");
            return new RedirectView("/my-section/wishlist");
        }
        System.out.println("wishlist removed");
        attributes.addFlashAttribute("successAttribute", "Book Successfully Removed from wishlist");
        return new RedirectView("/my-section/wishlist");
    }
}
