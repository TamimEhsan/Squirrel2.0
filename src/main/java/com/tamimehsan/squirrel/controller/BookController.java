package com.tamimehsan.squirrel.controller;

import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.services.BookService;
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
@RequestMapping("/books")
public class BookController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ModelAndView getAllBook(@RequestParam(name="page",required = false,defaultValue = "1") String page){
        // Getting user info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        // get Required data
        ArrayList<Book> books = bookService.getAllBook();
        ArrayList<Book> slicedBooks;

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
            slicedBooks= new ArrayList<>(books.subList(offset,offset+limits));
        else
            slicedBooks= new ArrayList<>(books.subList(offset,books.size()));
        Double pages = Double.valueOf(books.size());
        pages = Math.ceil(pages/limits);

        // save model

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/book/allBooksPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("books",slicedBooks);
        modelAndView.addObject("navbar",1);
        modelAndView.addObject("start",offset);
        modelAndView.addObject("page",offsetPage);
        modelAndView.addObject("pages",pages);
        modelAndView.addObject("cnt",books.size());
        modelAndView.addObject("target","/books?page=");

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getBookById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUserName(username);

        Integer bookId = 0;
        try{
            bookId = Integer.parseInt(id);
        }catch (Exception e){
            ;
        }

        Book book = bookService.getBooksById(bookId);
        if( book.genre == null ) book.genre = "";
        String[] genres = book.genre.split(",");

        ModelAndView modelAndView = new ModelAndView("user/base");
        modelAndView.addObject("bodyView","user/body/book/bookPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("book",book);
        modelAndView.addObject("genres",genres);
        modelAndView.addObject("navbar",1);

        return modelAndView;
    }
}
