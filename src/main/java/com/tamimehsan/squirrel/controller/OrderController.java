package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.entity.Picked;
import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.entity.WishList;
import com.tamimehsan.squirrel.services.CartService;
import com.tamimehsan.squirrel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/my-section/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ModelAndView getAllOrders(@RequestParam(name="orderStatus",required = false,defaultValue = "0") String status){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        Integer orderStatus = 0;
        orderStatus = Integer.parseInt(status);
        ArrayList<Cart> carts;
        if( orderStatus == 0 )
            carts = cartService.getAllCart(user.getUserId());
        else
            carts = cartService.getAllCart(user.getUserId(),orderStatus);
        for(Cart cart:carts){
            System.out.println(cart);
        }

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/allOrdersPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("orders",carts);
        modelAndView.addObject("navbar",-1);
        modelAndView.addObject("status",orderStatus);
        modelAndView.addObject("statuses",new String[]{"PROCESSING", "APPROVED", "ON SHIPPING", "SHIPPED", "COMPLETED", "CANCELLED", "RETURNED"});
        return modelAndView;
    }

    @GetMapping("/track/{id}")
    public ModelAndView trackOrder(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        Integer orderId = Integer.parseInt(id);

        Cart cart = cartService.getOneCart(orderId);
        for(Picked picked:cart.getPickeds()){
            System.out.println(picked);
        }
        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/orderPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("order",cart);
        modelAndView.addObject("navbar",-1);
        modelAndView.addObject("statuses",new String[]{"PROCESSING", "APPROVED", "ON SHIPPING", "SHIPPED", "COMPLETED", "CANCELLED", "RETURNED"});
        return modelAndView;
    }

}
