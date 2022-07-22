package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.repository.BookRepository;
import com.tamimehsan.squirrel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book getBooksById(int id){
        return bookRepository.getBookByBookId(id);
    }

    public ArrayList<Book> getAllBook(){
        return bookRepository.findAll();
    }

    public long countAllBooks(){
        return bookRepository.count();
    }
}
