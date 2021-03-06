package com.pfchoice.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.Contact;
import com.pfchoice.springboot.repositories.intf.RecordDetailsAwareRepository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>
		               , RecordDetailsAwareRepository<Contact, Integer>{

}
