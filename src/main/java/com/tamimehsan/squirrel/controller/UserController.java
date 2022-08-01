package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.*;
import com.tamimehsan.squirrel.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/")
    public ModelAndView home(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);
        Author author = authorService.getAuthorById(22);
        System.out.println(user);
//        System.out.println(author);
        Book book = bookService.getBooksById(2);
        ArrayList<Book> books = bookService.getAllBook();
//        System.out.println(books);
//        System.out.println(book);
        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("name","Kenobi");
        modelAndView.addObject("view","user/fragment");
        modelAndView.addObject("bodyView","user/body/homePage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("books",books);
        String[] ci = new String[]{"images/HomeCarousel1.png", "images/HomeCarousel2.png", "images/HomeCarousel3.png", "images/HomeCarousel4.png", "images/HomeCarousel5.png"};
        modelAndView.addObject("carousalImages",ci);
        return modelAndView;
    }


    @GetMapping("/login")
    public ModelAndView login(ModelMap model, String error, String logout, String verified) {
        System.out.println("hello general kenobi");
        System.out.println(error);
        if (error != null) {
            model.put("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            model.put("logout", "You have been logged out successfully.");
        }

        model.put("user", new User());
        ModelAndView mv = new ModelAndView("user/base");
        String[] ci = new String[]{"images/loginCarousel1.png", "images/loginCarousel2.png", "images/loginCarousel3.png", "images/loginCarousel4.png", "images/loginCarousel5.png", "images/loginCarousel6.png"};
        mv.addObject("carousalImages",ci);
        mv.addObject("bodyView","user/loginnew");
        mv.addObject("verified", verified);
        return mv;
    }

    @GetMapping("/signup")
    public ModelAndView register(ModelMap model, String error){
        model.put("customer", new User());

        model.put("user", new User());
        ModelAndView mv = new ModelAndView("user/base");
        String[] ci = new String[]{"images/loginCarousel1.png", "images/loginCarousel2.png", "images/loginCarousel3.png", "images/loginCarousel4.png", "images/loginCarousel5.png", "images/loginCarousel6.png"};
        mv.addObject("carousalImages",ci);
        mv.addObject("bodyView","user/signup");
        mv.addObject("hasErrors",false);
//        if(error != null) {
//            model.put("error", "This username is already taken");
//        }
        return mv;
    }
    @PostMapping("/signup")
    public ModelAndView register(@Validated @ModelAttribute("user") User customer, BindingResult bindingResult, ModelMap modelMap){
        List<String> errors = new ArrayList<>();
        customer.setAuthority("ROLE_CUSTOMER");
        System.out.println(customer);
        if ( userService.findByUserName(customer.getUsername()) !=null ) {
//            bindingResult.rejectValue("username", "", "This username has already been taken!");
            errors.add("User name already exists");
        }else if( !customer.getPassword().equals(customer.getPassword2()) ){
           // bindingResult.rejectValue("password2", "", "Password doesn't match");
            System.out.println("pass mile na");
            errors.add("Password doesn't match");
        }

        System.out.println("aaaaaaavvvvvvvvvvccccccccccc");
        System.out.println(customer);

        if (errors.size()>0) {
            ModelAndView mv = new ModelAndView("user/base");
            String[] ci = new String[]{"images/loginCarousel1.png", "images/loginCarousel2.png", "images/loginCarousel3.png", "images/loginCarousel4.png", "images/loginCarousel5.png", "images/loginCarousel6.png"};
            mv.addObject("carousalImages",ci);
            mv.addObject("hasErrors",true);
            mv.addObject("errors",errors);
            mv.addObject("bodyView","user/signup");
            return mv;
        }


        User user = userService.save(customer);
        System.out.println("after saving user:: "+user);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setUserId(user.getUserId());
        user.setCart(cart);
        System.out.println("Cart after inserting user:: "+cart);
        cart = cartService.save(cart);

        user = userService.save(user);
        return new ModelAndView("redirect:/login", modelMap);
//        User user = new User();
//        user.setUsername(customer.getUsername());
//        user.setPassword(customer.getPassword());
//        user.setAuthority("ROLE_CUSTOMER");
//        user.setEnabled(true);
       // user.setVerification(RandomString.make(64));
      //  userService.save(user);
//        userService.save(customer);
//        String content = "Dear [[name]],\n"
//                + "Please click the link below to verify your registration:\n"
//                + "link\n[[URL]]\n"
//                + "Thank you,\n"
//                + "KenaKata";
//        content = content.replace("[[name]]", customer.getFname()+" "+customer.getLname());
//        String verifyURL =  rootPath+"/verify?code=" + user.getVerification();
//        content = content.replace("[[URL]]", verifyURL);
//        emailSenderService.sendSimpleEmail(customer.getEmail(),content,"Verification of Email");
//        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }

}
