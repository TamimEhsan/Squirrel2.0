package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.Publisher;
import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.services.AuthorService;
import com.tamimehsan.squirrel.services.BookService;
import com.tamimehsan.squirrel.services.PublisherService;
import com.tamimehsan.squirrel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
        System.out.println(books);
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
        System.out.println("hello general kenobis");
        if (error != null) {
            model.put("error", "Your username or password is invalid.");
        }

        if (logout != null) {
            model.put("logout", "You have been logged out successfully.");
        }

        model.put("user", new User());
        ModelAndView mv = new ModelAndView("user/login");
        mv.addObject("verified", verified);
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelMap modelMap, String error){
        modelMap.put("customer", new Customer());

        ModelAndView mv = new ModelAndView("user/register");
        if(error != null) {
            modelMap.put("error", "This username is already taken");
        }
        return mv;
    }
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult, ModelMap modelMap){
        if (foundDuplicateUsername(customer.getUsername())) {
            bindingResult.rejectValue("username", "", "This username has already been taken!");
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/register?error=error";
        }
        User user = new User();
        user.setUsername(customer.getUsername());
        user.setPassword(customer.getPassword());
        user.setAuthority("ROLE_CUSTOMER");
        user.setEnabled(false);
        user.setVerification(RandomString.make(64));
        userService.save(user);
        customerService.save(customer);
        String content = "Dear [[name]],\n"
                + "Please click the link below to verify your registration:\n"
                + "link\n[[URL]]\n"
                + "Thank you,\n"
                + "KenaKata";
        content = content.replace("[[name]]", customer.getFname()+" "+customer.getLname());
        String verifyURL =  rootPath+"/verify?code=" + user.getVerification();
        content = content.replace("[[URL]]", verifyURL);
        emailSenderService.sendSimpleEmail(customer.getEmail(),content,"Verification of Email");
        return "redirect:/emailVerify";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }

}
