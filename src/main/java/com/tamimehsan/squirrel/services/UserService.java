package com.tamimehsan.squirrel.services;

import com.tamimehsan.squirrel.entity.User;
import com.tamimehsan.squirrel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    @Transactional
    public User save(User user){
        System.out.println("before encoding::"+user.getPassword());
        if( user.getUserId() == 0 )
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println("after encoding::"+user.getPassword());
        return userRepository.save(user);
    }
}
