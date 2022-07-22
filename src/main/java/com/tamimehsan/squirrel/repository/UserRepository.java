package com.tamimehsan.squirrel.repository;

import com.tamimehsan.squirrel.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer> {

    @Query("select * from users where username=:userName")
    public User findByUsername(@Param( "userName") String userName);

    public User findByVerification( String verification);
}
