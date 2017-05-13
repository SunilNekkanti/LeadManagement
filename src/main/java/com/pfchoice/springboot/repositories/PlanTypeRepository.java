package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.PlanType;

@Repository
public interface PlanTypeRepository extends JpaRepository<PlanType, Long> {


}

