package com.pfchoice.springboot.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findByUsername(String name);

}

