package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.Provider;

public  class ProviderSpecifications  implements Specification<Provider> {
 
    
    private String searchTerm;

    public ProviderSpecifications( String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }
    
    public Predicate toPredicate(Root<Provider> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

    	  String containsLikePattern = getContainsLikePattern(searchTerm);
    	 
    	  cq.distinct(true);
          return cb.or(
                  cb.like(cb.lower(root.get("name")), containsLikePattern),
                  cb.like(cb.lower(root.get("phone")), containsLikePattern),
                  cb.like(cb.lower(root.get("hoursOfOperation")), containsLikePattern),
                  cb.like(cb.lower(root.get("address")), containsLikePattern),
                  cb.like(cb.lower(root.get("ageSeen")), containsLikePattern), 
                  cb.like(root.join("languages").get("description"), containsLikePattern),
                  cb.like(root.join("brokerages").get("description"), containsLikePattern)
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