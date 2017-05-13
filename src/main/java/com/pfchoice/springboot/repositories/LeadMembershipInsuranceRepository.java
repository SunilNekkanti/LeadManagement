package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadMembershipInsurance;

@Repository
public interface LeadMembershipInsuranceRepository extends JpaRepository<LeadMembershipInsurance, Long> {


}

