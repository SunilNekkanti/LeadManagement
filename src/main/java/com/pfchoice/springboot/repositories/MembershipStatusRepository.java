package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.MembershipStatus;

@Repository
public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, Long> {


}

