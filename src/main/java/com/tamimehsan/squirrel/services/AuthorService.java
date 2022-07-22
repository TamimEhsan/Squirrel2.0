package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Author;
import com.tamimehsan.squirrel.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(int id){
        return authorRepository.getAllByAuthorId(id);
    }

    public ArrayList<Author> getAllAuthor(){
        return authorRepository.findAll();
    }
}
