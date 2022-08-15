package com.tamimehsan.squirrel.admin;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.services.AuthorService;
import com.tamimehsan.squirrel.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin/author")
public class AdminAuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public ModelAndView getAllAuthor(){
        ArrayList<Author> authors = authorService.getAllAuthor();
        ModelAndView modelAndView = new ModelAndView("admin/base");
        modelAndView.addObject("bodyView","admin/author/adminAuthorAll");
        modelAndView.addObject("authors",authors);
        return modelAndView;
    }
}
