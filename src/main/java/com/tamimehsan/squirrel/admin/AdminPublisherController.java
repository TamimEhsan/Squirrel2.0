package com.tamimehsan.squirrel.admin;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.entity.Publisher;
import com.tamimehsan.squirrel.services.AuthorService;
import com.tamimehsan.squirrel.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin/publisher")
public class AdminPublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("")
    public ModelAndView getAllPublisher(){
        ArrayList<Publisher> publishers = publisherService.getAllPublisher();
        ModelAndView modelAndView = new ModelAndView("admin/base");
        modelAndView.addObject("bodyView","admin/publisher/adminPublisherAll");
        modelAndView.addObject("publishers",publishers);
        return modelAndView;
    }
}
