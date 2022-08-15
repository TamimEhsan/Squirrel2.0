package com.tamimehsan.squirrel.admin;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.entity.Cart;
import com.tamimehsan.squirrel.services.AuthorService;
import com.tamimehsan.squirrel.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ModelAndView getAllOrder(){
        ArrayList<Cart> carts = cartService.getAllUncompleteOrder();
        System.out.println("uncomplete carts::"+carts);
        ModelAndView modelAndView = new ModelAndView("admin/base");
        modelAndView.addObject("bodyView","admin/order/adminOrderAll");
        modelAndView.addObject("orders",carts);
        return modelAndView;
    }

    @GetMapping("/update")
    public ModelAndView updateOrder(@RequestParam(name="cartId",required = false,defaultValue = "0") String id1,
                                    @RequestParam(name="orderStatus",required = false,defaultValue = "0") String stat){
        int cartId = 0;
        int orderStatus = 0;
        try{
            cartId = Integer.parseInt(id1);
            orderStatus = Integer.parseInt(stat);
        }catch (Exception e){
            ;
        }
        Cart cart = cartService.getOneCart(cartId);
        cart.setState(orderStatus);
        cartService.save(cart);
        return new ModelAndView("redirect:/admin/order");
    }
}
