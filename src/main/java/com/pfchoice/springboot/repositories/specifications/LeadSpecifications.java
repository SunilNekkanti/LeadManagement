package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.LeadMembership;

public class LeadSpecifications implements Specification<LeadMembership> {
	
	private String roleName;
	private String searchTerm;
	private Integer userId;
	private String username;

	public LeadSpecifications(Integer userId, String username, String roleName, String searchTerm) {
		super();
		this.roleName = roleName;
		this.searchTerm = searchTerm;
		this.userId = userId;
		this.username = username;
	}

	public Predicate toPredicate(Root<LeadMembership> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		String containsLikePattern = getContainsLikePattern(searchTerm);

		Predicate p = cb.conjunction();
		p.getExpressions()
				.add(cb.or(cb.like(cb.lower(root.get("firstName")), containsLikePattern),
						cb.like(cb.lower(root.get("lastName")), containsLikePattern),
						cb.like(root.join("gender").get("description"), containsLikePattern),
						cb.like(root.join("language").get("description"), containsLikePattern),
						cb.like(root.join("status").get("description"), containsLikePattern)

		));
		
		if("EVENT_COORDINATOR".equals(roleName)){
			p.getExpressions().add(cb.and(cb.equal(root.get("createdBy").as(String.class), username)));
		}
		if("AGENT".equals(roleName) ){
			p.getExpressions().add(cb.and(cb.equal((root.join("agentLeadAppointmentList").join("user").get("id").as(Integer.class)), userId)));
		}
		p.getExpressions().add(cb.and(cb.equal(root.get("activeInd"), 'Y')));
		return p;

	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}
}