package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.Book;
import com.tamimehsan.squirrel.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BookRepository extends CrudRepository<Book,Integer> {

    // get all books

//    @Query("SELECT * FROM Book ORDER BY name LIMIT=:limit OFFSET= :offset;")
//    public Book getAllBooks(@Param( "offset") int offset, @Param( "limit") int limit);

    public Book getBookByBookId(int book_id);
    // get book by id
    // get book by author id
    public ArrayList<Book> findAll();

    public long count();
}
