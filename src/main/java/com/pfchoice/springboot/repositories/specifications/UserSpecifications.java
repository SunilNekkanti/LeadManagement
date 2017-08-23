package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.User;

public  class UserSpecifications  implements Specification<User> {
 
    
    private String searchTerm;

    public UserSpecifications( String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }
    
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

    	  String containsLikePattern = getContainsLikePattern(searchTerm);
    	  cq.distinct(true);
          return cb.or(
                  cb.like(cb.lower(root.get("username")), containsLikePattern),
                  cb.like(cb.lower(root.get("phone")), containsLikePattern),
                  cb.like(cb.lower(root.get("email")), containsLikePattern),
                  cb.like(cb.lower(root.get("licenseNo")), containsLikePattern),
                  cb.like(root.join("language", JoinType.LEFT).get("description"), containsLikePattern),
                  cb.like(root.join("insurances", JoinType.LEFT).get("name"), containsLikePattern),
                  cb.like(root.join("counties", JoinType.LEFT).get("description"), containsLikePattern),
                  cb.like(root.join("roles").get("role"), containsLikePattern)
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