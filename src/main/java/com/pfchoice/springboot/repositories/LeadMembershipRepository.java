package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadMembershipProvider;

@Repository
public interface LeadMembershipRepository extends JpaRepository<LeadMembershipProvider, Long> {


}

