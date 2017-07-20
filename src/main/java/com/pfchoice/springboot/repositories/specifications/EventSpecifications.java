package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.Event;

public  class EventSpecifications  implements Specification<Event> {
 
    
    private String searchTerm;

    public EventSpecifications( String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }
    
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

    	  String containsLikePattern = getContainsLikePattern(searchTerm);
    	 
    	  cq.distinct(true);
          return cb.or(
                  cb.like(cb.lower(root.get("eventName")), containsLikePattern),
                 // cb.like(cb.lower(root.get("eventDateStartTime")), containsLikePattern),
                 // cb.like(cb.lower(root.get("eventDateEndTime")), containsLikePattern),
                  cb.like(root.join("brokerage").get("description"), containsLikePattern),
                  cb.like(root.join("facilityType").get("description"), containsLikePattern),
                  cb.like(cb.lower(root.get("notes")), containsLikePattern),
                  cb.like(root.join("eventTemplate").get("name"), containsLikePattern),
                  cb.like(root.join("activityType").get("description"), containsLikePattern)
                //  cb.like(cb.lower(root.get("contactPhone")), containsLikePattern),
                 
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