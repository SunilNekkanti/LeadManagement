package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

}

