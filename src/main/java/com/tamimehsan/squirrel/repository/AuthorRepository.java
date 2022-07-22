package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AuthorRepository extends CrudRepository<Author,Integer> {

    public Author getAllByAuthorId(int id);

    public ArrayList<Author> findAll();
}
