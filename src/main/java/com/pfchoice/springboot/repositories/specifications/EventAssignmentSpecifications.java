package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.EventAssignment;

public class EventAssignmentSpecifications implements Specification<EventAssignment> {

	private String searchTerm;
	private String roleName;
	private Integer userId;

	public EventAssignmentSpecifications(Integer userId, String roleName, String searchTerm) {
		super();
		this.searchTerm = searchTerm;
		this.roleName = roleName;
		this.userId = userId;
	}

	public Predicate toPredicate(Root<EventAssignment> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		String containsLikePattern = getContainsLikePattern(searchTerm);
		cq.distinct(true);

		Predicate p = cb.conjunction();
		p.getExpressions().add(cb.or(cb.like(root.join("event").get("eventName"), containsLikePattern)

		));
		if("AGENT".equals(roleName) || "EVENT_COORDINATOR".equals(roleName) ){
			p.getExpressions().add(cb.and(cb.equal(root.join("representatives").get("id").as(Integer.class), userId)));
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