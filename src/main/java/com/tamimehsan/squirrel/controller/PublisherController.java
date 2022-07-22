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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private UserService userService;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("")
    public ModelAndView getAllPublisher(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);



        ArrayList<Publisher> publishers = publisherService.getAllPublisher();

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/publisher/allPublishersPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("publishers",publishers);
        modelAndView.addObject("navbar",3);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getPublisherById(@PathVariable String id,
                                            @RequestParam(name="page",required = false,defaultValue = "1") String page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        Integer publisherId = 0;
        try{
            publisherId = Integer.parseInt(id);
        }catch (Exception e){
            ;
        }

        Publisher publisher = publisherService.getPublisherById(publisherId);
        List<Book> books = publisher.books;
        List<Book> slicedBooks;

        // pagination
        int limits = 3;
        int offsetPage = 1;
        try{
            offsetPage = Integer.parseInt(page);
        }catch (Exception e){
            ;
        }
        int offset = (offsetPage-1)*limits;

        if( offset+limits<=books.size() )
            slicedBooks= books.subList(offset,offset+limits);
        else
            slicedBooks= books.subList(offset,books.size());
        Double pages = Double.valueOf(books.size());
        pages = Math.ceil(pages/limits);

        System.out.println(publisher.books);
        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/publisher/publisherPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("publisher",publisher);
        modelAndView.addObject("books",slicedBooks);
        modelAndView.addObject("navbar",3);
        modelAndView.addObject("start",offset);
        modelAndView.addObject("page",offsetPage);
        modelAndView.addObject("pages",pages);
        modelAndView.addObject("cnt",books.size());
        modelAndView.addObject("target","/publishers/"+publisherId+"?page=");
        return modelAndView;
    }


}
