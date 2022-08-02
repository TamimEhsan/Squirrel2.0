package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/my-section/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ModelAndView getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);


        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/profile");
        modelAndView.addObject("user",user);
        modelAndView.addObject("navbar",-1);
        return modelAndView;
    }
    @GetMapping("/edit")
    public ModelAndView getEditProfile(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        model.put("user",user);

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/user/profileEdit");
        modelAndView.addObject("user",user);
        modelAndView.addObject("navbar",-1);
        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView postEditProfile(@Validated @ModelAttribute("user") User editedUser, BindingResult bindingResult, ModelMap modelMap){
//        userService.save(editedUser);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        user.setFirst_name(editedUser.getFirst_name());
        user.setLast_name(editedUser.getLast_name());
        user.setImage(editedUser.getImage());
        userService.save(user);
        System.out.println("henlo :: from edit profile post controller");
        return new ModelAndView("redirect:/my-section/profile");
    }
}
