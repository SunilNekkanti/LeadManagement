package com.pfchoice.springboot.repositories.specifications;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.Event;

public  class EventSpecifications  implements Specification<Event> {
 
    
    private String searchTerm;
    private String roleName;
	private Integer userId;

    public EventSpecifications(Integer userId, String roleName, String searchTerm) {
        super();
        this.searchTerm = searchTerm;
        this.roleName = roleName;
		this.userId = userId;
    }
    
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {

    	  String containsLikePattern = getContainsLikePattern(searchTerm);
    	 
    	  cq.distinct(true);
    	  
    	  Predicate p = cb.conjunction();
    	  
    	  if(searchTerm != null) {
    		  p.getExpressions()
              .add(
                cb.or(
                		   cb.like(root.get("eventName"), containsLikePattern),
                		   cb.like(root.get("eventDateStartTime").as(String.class), containsLikePattern),
                           cb.like(root.get("eventDateEndTime").as(String.class), containsLikePattern),
                           cb.like(root.join("facilityType").get("description"), containsLikePattern),
                           cb.like(root.join("contact").get("contactPerson"), containsLikePattern),
                           cb.like(root.join("contact").get("mobilePhone"), containsLikePattern)
                           
              ));
    	  }
    	  
    	  if("AGENT".equals(roleName) || "EVENT_COORDINATOR".equals(roleName) ){
  			p.getExpressions().add(cb.and(cb.equal(root.join("eventAssignments").join("representatives").get("id").as(Integer.class), userId)));
  			
  			Expression<Date> eventStartTime = root.get("eventDateStartTime");
  			Expression<Date> eventEndTime = root.get("eventDateEndTime");

		    Calendar currentTime = Calendar.getInstance();
		    Date currentDate = currentTime.getTime();
		    
			p.getExpressions().add(cb.and(cb.between(cb.literal(currentDate), eventStartTime, eventEndTime)));
  		  }	
    	  
    	  p.getExpressions()
          .add( cb.and(cb.equal(root.get("activeInd"),'Y')));
          return p;
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