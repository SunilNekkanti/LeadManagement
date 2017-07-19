package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.EventTemplate;

public  class EventTemplateSpecifications  implements Specification<EventTemplate> {
 
    
    private String searchTerm;

    public EventTemplateSpecifications( String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }
    
    public Predicate toPredicate(Root<EventTemplate> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

    	  String containsLikePattern = getContainsLikePattern(searchTerm);
    	 
    	  cq.distinct(true);
          return cb.or(
                  cb.like(cb.lower(root.get("name")), containsLikePattern),
                  cb.like(cb.lower(root.get("address1")), containsLikePattern),
                  cb.like(cb.lower(root.get("address2")), containsLikePattern),
                  cb.like(cb.lower(root.get("city")), containsLikePattern),
                  cb.like(cb.lower(root.get("contactPerson")), containsLikePattern),
                //  cb.like(cb.lower(root.get("contactPhone")), containsLikePattern),
                  cb.like(root.join("state").get("description"), containsLikePattern)
               //   cb.like(root.join("zipCode").get("code"), containsLikePattern)
          );
    }
 
 
    private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
}