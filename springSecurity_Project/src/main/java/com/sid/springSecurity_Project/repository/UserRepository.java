package com.sid.springSecurity_Project.repository;


import com.sid.springSecurity_Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    //To check the email
    Optional<User>findByEmail(String email);
}
