package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PublisherRepository extends CrudRepository<Publisher,Integer> {

    public Publisher getAllByPublisherId(Integer id);

    public ArrayList<Publisher> findAll();
}
