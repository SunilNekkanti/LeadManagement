package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.FrequencyType;

@Repository
public interface FrequencyTypeRepository extends JpaRepository<FrequencyType, Long> {


}

