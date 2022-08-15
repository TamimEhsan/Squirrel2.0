package com.tamimehsan.squirrel.admin;

import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin/book")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ModelAndView getAllBook(@RequestParam(name="page",required = false,defaultValue = "1") String page){
        // Getting user info

        // get Required data
        ArrayList<Book> books = bookService.getAllBook();
        ArrayList<Book> slicedBooks;

        // pagination
        int limits = 50;
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

        ModelAndView modelAndView = new ModelAndView("admin/base");
        modelAndView.addObject("bodyView","admin/book/adminBookAll");
        modelAndView.addObject("books",slicedBooks);

        modelAndView.addObject("start",offset);
        modelAndView.addObject("page",offsetPage);
        modelAndView.addObject("pages",pages);
        modelAndView.addObject("cnt",books.size());
        modelAndView.addObject("target","/admin/book?page=");

        return modelAndView;
    }
}
