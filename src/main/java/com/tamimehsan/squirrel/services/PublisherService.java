package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.Publisher;
import com.tamimehsan.squirrel.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher getPublisherById(Integer id){
        return publisherRepository.getAllByPublisherId(id);
    }

    public ArrayList<Publisher> getAllPublisher(){
        return publisherRepository.findAll();
    }
}
