package com.tamimehsan.squirrel.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.entity.Picked;
import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.entity.WishList;
import com.tamimehsan.squirrel.repository.CartRepository;
import com.tamimehsan.squirrel.services.CartService;
import com.tamimehsan.squirrel.services.UserService;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    @GetMapping("")
    public ModelAndView getCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);



        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/cartPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("navbar",-1);
        modelAndView.addObject("items",user.getCart().getPickeds());
        return modelAndView;
    }

    // update cart
    @PostMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody String stringBody){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        stringBody = decode(stringBody);
        System.out.println(stringBody);
        System.out.println(new JSONArray(stringBody));
        cartService.updateCart(user.getCart().cartId,new JSONArray(stringBody));
        return ResponseEntity.ok().build();
    }
    // controller which returns http status code 200
    @GetMapping("/delete/{id}")
    public RedirectView deleteFromCart(@PathVariable("id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id);

        cartService.deleteItemFromCart(user.getCart().cartId,bookId);
        return new RedirectView("/cart");
    }

//    @GetMapping("/{id}")
//    public ModelAndView getBookById(@PathVariable String id){}

    @GetMapping("/add/{id}")
    public RedirectView addBookToCart(@PathVariable String id, RedirectAttributes attributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int bookId = Integer.parseInt(id);

        Picked picked = cartService.getItemInCart(user.getCart().cartId,bookId);
        if (picked == null ){
            // add book to cart
            cartService.addItemToCart(user.getCart().cartId,bookId);
        }else{
            attributes.addFlashAttribute("errorAttribute", "Book Already Added in cart");
            return new RedirectView("/books/"+id);
        }
        attributes.addFlashAttribute("successAttribute", "Book Successfully Added to cart");
        return new RedirectView("/books/"+id);
//        return new ModelAndView("redirect:/books/"+id,"error","successs");
    }

    // confirm cart purchase
    @GetMapping("/confirm")
    public ModelAndView confirmCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);
        cartService.confirmCart(user.getCart().cartId,user.getUserId());
        return new ModelAndView("redirect:/cart");
    }
    @PostMapping("/confirmOrder")
    public ModelAndView confirmCart2(@Validated @ModelAttribute("cart") Cart cart){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);
        System.out.println(cart);
        Cart oldCart = user.getCart();
        oldCart.setAddress(cart.getAddress());
        oldCart.setPhone1(cart.getPhone1());
        oldCart.setPhone2(cart.getPhone2());
        oldCart.setName(cart.getName());
        oldCart.setPick(cart.getPick());
        oldCart.setPaymentMethod(cart.getPaymentMethod());
        cartService.save(oldCart);
        cartService.confirmCart(user.getCart().cartId,user.getUserId());
        return new ModelAndView("redirect:/cart");
    }
    @GetMapping("/ship")
    public ModelAndView getShipment(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        int totalPrice = 0;
        for (Picked picked : user.getCart().getPickeds()) {
            totalPrice += picked.getBook().getPrice()*picked.getAmount();
        }
        Cart cart = user.getCart();
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(user.getCart().getPickeds().size());
        cartService.save(cart);

        model.put("cart", cart);


        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/placeOrder");
        modelAndView.addObject("user",user);
        modelAndView.addObject("cart",cart);
        modelAndView.addObject("navbar",-1);
        return modelAndView;
    }
}
